# Automation Testing Project

This project contains automated tests for Sauce Demo website and Restful Booker API.

## Technologies Used

- Java 11
- Selenium WebDriver 4.15.0
- REST Assured 5.3.2
- JUnit 5.10.0
- Maven

## Project Structure

- `/src/test/java/com/automation/tests/` - Selenium WebDriver tests
- `/src/test/java/com/automation/api/tests/` - REST Assured API tests
- `/src/test/java/com/automation/pages/` - Page Object Model classes
- `/src/test/java/com/automation/base/` - Base test classes

## Test Scenarios

### Selenium Tests (Sauce Demo)

1. Product Sorting Test
2. Shopping Cart Functionality Test
3. Checkout Process Test
4. Product Details Test
5. User Menu Test

### API Tests (Restful Booker)

1. Get All Bookings Test
2. Get Specific Booking Test
3. Create Booking Test
4. Update Booking Test
5. Authentication Test

## Setup and Execution

1. Clone the repository
2. Install Java 11+ and Maven
3. Run tests: `mvn test`
4. View reports: `mvn surefire-report:report`
