package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

public class ProductDetailsTest extends BaseTest {

    @Test
    public void testViewProductDetails() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        // Click on first product
        String expectedProductName = driver.findElement(By.css(".inventory_item_name"))
                .getText();
        driver.findElement(By.css(".inventory_item_name")).click();

        // Wait for product details page
        wait.until(ExpectedConditions.urlContains("inventory-item"));

        // Verify product details page loaded
        String actualProductName = driver.findElement(By.css(".inventory_details_name"))
                .getText();
        assertEquals(expectedProductName, actualProductName,
                "Product name should match on details page");

        // Verify back button works
        driver.findElement(By.id("back-to-products")).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
        assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Should return to inventory page");
    }
}