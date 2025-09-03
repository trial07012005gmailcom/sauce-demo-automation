package com.automation.api.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.*;

public class BaseAPITest {
    protected static final String BASE_URI = "https://restful-booker.herokuapp.com";
    protected static String authToken;

    @BeforeAll
    public static void setupAPI() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Get authentication token
        String credentials = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        Response authResponse = given()
                .contentType("application/json")
                .body(credentials)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract().response();

        authToken = authResponse.jsonPath().getString("token");
    }

    protected String createSampleBooking() {
        String bookingData = "{\n" +
                "    \"firstname\" : \"John\",\n" +
                "    \"lastname\" : \"Doe\",\n" +
                "    \"totalprice\" : 150,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-01-01\",\n" +
                "        \"checkout\" : \"2024-01-05\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(bookingData)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().response();

        return response.jsonPath().getString("bookingid");
    }
}