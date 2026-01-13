package com.davidsafta.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HapifyMeFullFlowTest {

    private String email;
    private String username;
    private final String password = "Password123!";
    private int userId;
    private String apiKey;
    private String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://test.hapifyme.com/api";
    }

    @Test
    public void register_login_get_profile_and_delete_profile() {
        // 1) REGISTER – generează email dinamic și salvează user_id + api_key + username
        long timestamp = System.currentTimeMillis();
        email = "test_user_" + timestamp + "@example.com";

        Response registerResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body("{"
                                + "\"first_name\":\"Test\","
                                + "\"last_name\":\"User\","
                                + "\"email\":\"" + email + "\","
                                + "\"password\":\"" + password + "\""
                                + "}")
                        .when()
                        .post("/user/register.php")
                        .then()
                        .statusCode(201)
                        .body("status", equalTo("success"))
                        .extract().response();

        // user_id, api_key și username din răspunsul de la register
        String userIdStr = registerResponse.path("user_id").toString();
        userId = Integer.parseInt(userIdStr);

        apiKey = registerResponse.path("api_key");
        username = registerResponse.path("username"); // FOARTE IMPORTANT: username generat de server

        // 2) LOGIN – cu username primit la Register
        Response loginResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body("{"
                                + "\"username\":\"" + username + "\","
                                + "\"password\":\"" + password + "\""
                                + "}")
                        .when()
                        .post("/user/login.php")
                        .then()
                        .statusCode(200)
                        .body("status", equalTo("success"))
                        .extract().response();

        // token folosit la DELETE
        token = loginResponse.path("token");

        // user.id din răspunsul de login (vine ca String -> îl convertim în int)
        String userIdFromLoginStr = loginResponse.path("user.id").toString();
        int userIdFromLogin = Integer.parseInt(userIdFromLoginStr);

        // verificăm că user id din login = user id din register
        Assert.assertEquals(
                userIdFromLogin,
                userId,
                "User id from Login should match user id from Register"
        );

        // 3) Verificăm că JWT (token) conține același user_id
        Assert.assertNotNull(token, "Token should not be null");

        String[] parts = token.split("\\.");
        Assert.assertEquals(parts.length, 3, "JWT should have 3 parts");

        // decodăm payload-ul (partea a doua din JWT)
        String payloadJson = new String(
                Base64.getUrlDecoder().decode(parts[1]),
                StandardCharsets.UTF_8
        );

        JsonPath jwtJson = new JsonPath(payloadJson);
        String userIdFromJwtStr = jwtJson.getString("user_id");
        int userIdFromJwt = Integer.parseInt(userIdFromJwtStr);

        Assert.assertEquals(
                userIdFromJwt,
                userId,
                "user_id from JWT should match registered user_id"
        );

        // 4) GET PROFILE – folosind user_id și api_key
        RequestSpecification getProfileRequest =
                given()
                        .contentType(ContentType.JSON)
                        // API Key în header (varianta “curată”, fără Bearer aici)
                        .header("Authorization", apiKey)
                        .queryParam("user_id", userId);

        getProfileRequest
                .when()
                .get("/user/get_profile.php")
                .then()
                .statusCode(200)
                .body("user.email", equalTo(email));

        // 5) DELETE PROFILE – cu Bearer {token}
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/user/delete_profile.php")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"));
    }
}
