package com.automation.api.tests;

import com.automation.api.base.BaseAPITest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetSpecificBookingTest extends BaseAPITest {

    @Test
    public void testGetValidBooking() {
        // Create a booking first
        String bookingId = createSampleBooking();

        Response response = given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("firstname", equalTo("John"))
                .body("lastname", equalTo("Doe"))
                .body("totalprice", equalTo(150))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2024-01-01"))
                .body("bookingdates.checkout", equalTo("2024-01-05"))
                .body("additionalneeds", equalTo("Breakfast"))
                .extract().response();

        // Verify response structure
        assertNotNull(response.jsonPath().getString("firstname"),
                "First name should not be null");
        assertNotNull(response.jsonPath().getString("lastname"),
                "Last name should not be null");
        assertNotNull(response.jsonPath().get("bookingdates"),
                "Booking dates should not be null");
    }

    @Test
    public void testGetInvalidBooking() {
        given()
                .when()
                .get("/booking/999999")
                .then()
                .statusCode(404);
    }
}