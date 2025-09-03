package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductsPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest extends BaseTest {

    @Test
    public void testCompleteCheckoutProcess() {
        // Login and add product
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(0);
        productsPage.clickCart();

        // Proceed to checkout
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        driver.findElement(By.id("checkout")).click();

        // Fill checkout information
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        // Continue
        driver.findElement(By.id("continue")).click();

        // Finish checkout
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        driver.findElement(By.id("finish")).click();

        // Verify completion
        wait.until(ExpectedConditions.presenceOfElementLocated(By.css(".complete-header")));
        String completionMessage = driver.findElement(By.css(".complete-header")).getText();
        assertEquals("THANK YOU FOR YOUR ORDER", completionMessage,
                "Checkout should complete successfully");
    }
}