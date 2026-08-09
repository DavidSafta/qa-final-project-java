package api.tests;

import com.davidsafta.api.model.DeleteResponse;
import com.davidsafta.api.model.LoginResponse;
import com.davidsafta.api.model.RegisterResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class HapifyMeFullFlowTest {

    @BeforeClass
    public void setup() {
        // Baza pentru toate request-urile API
        RestAssured.baseURI = "https://apps.qualiadept.eu/hapifyme/api";
    }

    @Test
    public void register_confirm_login_get_profile_update_and_delete_profile() {
        // ---------- 1) REGISTER ----------
        String suffix = String.valueOf(System.currentTimeMillis());
        String email = "test_user_" + suffix + "@example.com";
        String password = "Password123!";

        String registerBodyJson = String.format(
                "{\"first_name\":\"%s\",\"last_name\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",
                "Test", "User", email, password
        );

        RegisterResponse registerResponse =
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(registerBodyJson)
                        .when()
                        .post("/user/register.php")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .body("status", equalTo("success"))
                        .extract()
                        .as(RegisterResponse.class);

        String apiKey = registerResponse.getApiKey();
        int userId  = registerResponse.getUserId();
        String username = registerResponse.getUsername();   // <- username generat de backend

        // ---------- 2) CONFIRM EMAIL ----------
        // 2a) Încercăm să luăm confirmation_token direct din răspunsul de la REGISTER
        String confirmationToken = registerResponse.getConfirmationToken();

        // 2b) Dacă nu vine în răspuns, îl așteptăm de pe /user/retrieve_token.php cu Awaitility
        if (confirmationToken == null || confirmationToken.isEmpty()) {
            confirmationToken =
                    await()
                            .atMost(20, SECONDS)
                            .pollInterval(2, SECONDS)
                            .until(
                                    () -> {
                                        Response r = given()
                                                .log().all()
                                                .queryParam("email", email)
                                                .when()
                                                .get("/user/retrieve_token.php");

                                        System.out.println("Retrieve token status code: " + r.getStatusCode());
                                        System.out.println("Retrieve token body: " + r.asString());

                                        if (r.getStatusCode() != 200) {
                                            return null; // mai încearcă
                                        }

                                        String token = r.jsonPath().getString("confirmation_token");
                                        return (token != null && !token.isEmpty()) ? token : null;
                                    },
                                    tokenValue -> tokenValue != null
                            );
        }

        // 2c) Confirmăm email-ul folosind confirmation_token
        // IMPORTANT: endpoint-ul vrea query param "token", nu "confirmation_token".
        given()
                .log().all()
                .queryParam("token", confirmationToken)
                .when()
                .get("/user/confirm_email.php")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("success"));

        // ---------- 3) LOGIN (cu username, NU cu email) ----------
        String loginBodyJson = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password
        );

        LoginResponse loginResponse =
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(loginBodyJson)
                        .when()
                        .post("/user/login.php")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("status", equalTo("success"))
                        .extract()
                        .as(LoginResponse.class);

        assertEquals(
                loginResponse.getUserId(),
                userId,
                "User id from login should match user id from register"
        );

        String token = loginResponse.getToken();

        // ---------- 4) GET PROFILE (READ inițial) ----------
        given()
                .log().all()
                .header("Authorization", apiKey)     // API Key în header
                .queryParam("user_id", userId)
                .when()
                .get("/user/get_profile.php")
                .then()
                .log().all()
                .statusCode(200)
                .body("user.email", equalTo(email));

        // ---------- 5) UPDATE PROFILE – schimbăm first_name (și trimitem câmpurile cerute) ----------
        String updatedFirstName = "Updated";

        given()
                .log().all()
                .contentType("application/json")
                // backend-ul vrea API key aici
                .header("Authorization", apiKey)
                .body("{\"user_id\": " + userId +
                        ", \"first_name\": \"" + updatedFirstName + "\"" +
                        ", \"last_name\": \"User\"" +
                        ", \"email\": \"" + email + "\"}")
                .when()
                .put("/user/update_profile.php")   // endpoint-ul acceptă PUT
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("success"));

        // 5b) GET PROFILE după UPDATE – verificăm că modificarea s-a aplicat
        given()
                .log().all()
                .header("Authorization", apiKey)
                .queryParam("user_id", userId)
                .when()
                .get("/user/get_profile.php")
                .then()
                .log().all()
                .statusCode(200)
                .body("user.first_name", equalTo(updatedFirstName))
                .body("user.email", equalTo(email));

        // ---------- 6) DELETE PROFILE – happy path (status = success) ----------
        DeleteResponse deleteResponse =
                given()
                        .log().all()
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .delete("/user/delete_profile.php")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("status", equalTo("success"))
                        .extract()
                        .as(DeleteResponse.class);

        // Verificăm din POJO că status-ul este "success"
        assertEquals(
                deleteResponse.getStatus(),
                "success",
                "Delete profile should return backend status 'success' in test env"
        );
    }
}