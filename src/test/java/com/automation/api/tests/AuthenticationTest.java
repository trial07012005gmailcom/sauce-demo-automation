package com.automation.api.tests;

import com.automation.api.base.BaseAPITest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest extends BaseAPITest {

    @Test
    public void testValidAuthentication() {
        String credentials = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(credentials)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("token", notNullValue())
                .extract().response();

        String token = response.jsonPath().getString("token");
        assertNotNull(token, "Token should not be null");
        assertTrue(token.length() > 0, "Token should not be empty");
    }

    @Test
    public void testInvalidAuthentication() {
        String invalidCredentials = "{\n" +
                "    \"username\" : \"invalid\",\n" +
                "    \"password\" : \"invalid\"\n" +
                "}";

        given()
                .contentType("application/json")
                .body(invalidCredentials)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void testHealthCheck() {
        Response response = given()
                .when()
                .get("/ping")
                .then()
                .statusCode(201)
                .extract().response();

        assertTrue(response.getTime() < 3000, "Health check should respond quickly");
        assertEquals("Created", response.getBody().asString(), "Should return 'Created'");
    }
}