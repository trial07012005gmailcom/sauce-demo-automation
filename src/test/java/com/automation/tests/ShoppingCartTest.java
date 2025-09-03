package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductsPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest extends BaseTest {

    @Test
    public void testAddMultipleProductsToCart() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        ProductsPage productsPage = new ProductsPage(driver);

        // Add first product
        productsPage.addProductToCart(0);
        assertEquals("1", productsPage.getCartBadgeText(), "Cart should show 1 item");

        // Add second product
        productsPage.addProductToCart(1);
        assertEquals("2", productsPage.getCartBadgeText(), "Cart should show 2 items");

        // Go to cart and verify items
        productsPage.clickCart();
        wait.until(ExpectedConditions.urlContains("cart"));

        List<WebElement> cartItems = driver.findElements(By.css(".cart_item"));
        assertEquals(2, cartItems.size(), "Cart should contain 2 items");
    }

    @Test
    public void testRemoveProductFromCart() {
        // Login and add product
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(0);

        // Go to cart
        productsPage.clickCart();
        wait.until(ExpectedConditions.urlContains("cart"));

        // Remove item
        driver.findElement(By.css(".cart_button")).click();

        // Verify item removed
        List<WebElement> cartItems = driver.findElements(By.css(".cart_item"));
        assertEquals(0, cartItems.size(), "Cart should be empty after removing item");
    }
}