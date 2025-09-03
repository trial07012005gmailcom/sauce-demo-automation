package com.automation.api.tests;

import com.automation.api.base.BaseAPITest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetBookingsTest extends BaseAPITest {

    @Test
    public void testGetAllBookings() {
        Response response = given()
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .extract().response();

        // Additional assertions
        assertTrue(response.getTime() < 5000, "Response time should be less than 5 seconds");
        assertNotNull(response.jsonPath().getList("bookingid"), "Booking IDs should not be null");
    }

    @Test
    public void testGetBookingsWithFirstNameFilter() {
        String firstName = "John";

        given()
                .queryParam("firstname", firstName)
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    public void testGetBookingsWithDateFilter() {
        given()
                .queryParam("checkin", "2024-01-01")
                .queryParam("checkout", "2024-01-05")
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }
}