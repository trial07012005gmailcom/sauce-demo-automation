package com.automation.api.tests;

import com.automation.api.base.BaseAPITest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateBookingTest extends BaseAPITest {

    @Test
    public void testCreateValidBooking() {
        String bookingData = "{\n" +
                "    \"firstname\" : \"Jane\",\n" +
                "    \"lastname\" : \"Smith\",\n" +
                "    \"totalprice\" : 200,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-02-01\",\n" +
                "        \"checkout\" : \"2024-02-07\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"WiFi\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(bookingData)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("booking.firstname", equalTo("Jane"))
                .body("booking.lastname", equalTo("Smith"))
                .body("booking.totalprice", equalTo(200))
                .body("booking.depositpaid", equalTo(true))
                .body("bookingid", notNullValue())
                .extract().response();

        // Verify booking ID is returned
        String bookingId = response.jsonPath().getString("bookingid");
        assertNotNull(bookingId, "Booking ID should be returned");
        assertTrue(Integer.parseInt(bookingId) > 0, "Booking ID should be positive");

        // Verify the created booking can be retrieved
        given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Jane"));
    }

    @Test
    public void testCreateBookingWithMissingFields() {
        String incompleteBookingData = "{\n" +
                "    \"firstname\" : \"John\"\n" +
                "}";

        given()
                .contentType("application/json")
                .body(incompleteBookingData)
                .when()
                .post("/booking")
                .then()
                .statusCode(500); // Internal Server Error due to missing required fields
    }

    @Test
    public void testCreateBookingWithInvalidData() {
        String invalidBookingData = "{\n" +
                "    \"firstname\" : \"\",\n" +
                "    \"lastname\" : \"\",\n" +
                "    \"totalprice\" : -100,\n" +
                "    \"depositpaid\" : \"invalid\",\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"invalid-date\",\n" +
                "        \"checkout\" : \"invalid-date\"\n" +
                "    }\n" +
                "}";

        given()
                .contentType("application/json")
                .body(invalidBookingData)
                .when()
                .post("/booking")
                .then()
                .statusCode(anyOf(is(400), is(500))); // Bad Request or Internal Server Error
    }
}