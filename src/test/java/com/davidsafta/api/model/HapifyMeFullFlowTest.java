package com.davidsafta.api.model;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class HapifyMeFullFlowTest {

    @BeforeClass
    public void setup() {
        // baza pentru toate request-urile API
        RestAssured.baseURI = "https://test.hapifyme.com/api";
    }

    @Test
    public void register_login_get_profile_and_delete_profile() {

        // ---------- 1) REGISTER ----------
        String suffix = String.valueOf(System.currentTimeMillis());
        String email = "test_user_" + suffix + "@example.com";
        String password = "Password123!";

        RegisterRequest registerBody =
                new RegisterRequest("Test", "User", email, password);

        RegisterResponse registerResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(registerBody)
                        .when()
                        .post("/user/register.php")
                        .then()
                        .statusCode(201)
                        .body("status", equalTo("success"))
                        .extract()
                        .as(RegisterResponse.class);

        String apiKey = registerResponse.api_key;
        int userIdFromRegister = registerResponse.user_id;
        String username = registerResponse.username;

        // ---------- 2) LOGIN ----------
        LoginRequest loginBody = new LoginRequest(username, password);

        LoginResponse loginResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(loginBody)
                        .when()
                        .post("/user/login.php")
                        .then()
                        .statusCode(200)
                        .body("status", equalTo("success"))
                        .extract()
                        .as(LoginResponse.class);

        String token = loginResponse.token;
        int userIdFromLogin = loginResponse.user.id;
        String emailFromLogin = loginResponse.user.email;

        // verificări Register vs Login
        assertEquals(userIdFromLogin, userIdFromRegister,
                "User id from login should match user id from register");
        assertEquals(emailFromLogin, email,
                "Email from login should match generated email");

        // ---------- 3) GET PROFILE (cu Awaitility + API Key) ----------

        ProfileResponse profileResponse =
                await()
                        .atMost(Duration.ofSeconds(5))
                        .pollInterval(Duration.ofMillis(500))
                        .until(() ->
                                        given()
                                                .header("Authorization", apiKey)
                                                .queryParam("user_id", userIdFromRegister)
                                                .when()
                                                .get("/user/get_profile.php")
                                                .then()
                                                .statusCode(200)
                                                .extract()
                                                .as(ProfileResponse.class),
                                pr -> "success".equalsIgnoreCase(pr.status)
                        );

        assertEquals(profileResponse.user.email, email,
                "Profile email should match generated email");

        // ---------- 4) DELETE PROFILE (Bearer token) ----------

        DeleteResponse deleteResponse =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .delete("/user/delete_profile.php")
                        .then()
                        .statusCode(200)
                        .body("status", equalTo("success"))
                        .extract()
                        .as(DeleteResponse.class);

        assertEquals(deleteResponse.status, "success",
                "Delete profile should return status success");
    }
}
