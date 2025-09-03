package com.automation.api.tests;

import com.automation.api.base.BaseAPITest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UpdateBookingTest extends BaseAPITest {

    @Test
    public void testUpdateBookingWithValidData() {
        // Create a booking first
        String bookingId = createSampleBooking();

        String updatedBookingData = "{\n" +
                "    \"firstname\" : \"Updated John\",\n" +
                "    \"lastname\" : \"Updated Doe\",\n" +
                "    \"totalprice\" : 250,\n" +
                "    \"depositpaid\" : false,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-03-01\",\n" +
                "        \"checkout\" : \"2024-03-10\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";

        given()
                .contentType("application/json")
                .cookie("token", authToken)
                .body(updatedBookingData)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Updated John"))
                .body("lastname", equalTo("Updated Doe"))
                .body("totalprice", equalTo(250))
                .body("depositpaid", equalTo(false))
                .body("additionalneeds", equalTo("Lunch"));
    }

    @Test
    public void testUpdateBookingWithoutAuthentication() {
        String bookingId = createSampleBooking();

        String updatedBookingData = "{\n" +
                "    \"firstname\" : \"Unauthorized Update\"\n" +
                "}";

        given()
                .contentType("application/json")
                .body(updatedBookingData)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(403); // Forbidden
    }
}