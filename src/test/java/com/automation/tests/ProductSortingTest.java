package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductsPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ProductSortingTest extends BaseTest {

    @Test
    public void testSortProductsByNameAscending() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        // Wait for products page to load
        wait.until(ExpectedConditions.urlContains("inventory"));

        ProductsPage productsPage = new ProductsPage(driver);

        // Sort by name A-Z
        productsPage.sortProducts("az");

        // Verify sorting
        List<String> productNames = productsPage.getProductNames();
        List<String> sortedNames = productNames.stream().sorted().collect(Collectors.toList());

        assertEquals(sortedNames, productNames, "Products should be sorted alphabetically A-Z");
    }

    @Test
    public void testSortProductsByPriceLowToHigh() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        ProductsPage productsPage = new ProductsPage(driver);

        // Sort by price low to high
        productsPage.sortProducts("lohi");

        // Verify sorting by price
        List<WebElement> priceElements = driver.findElements(By.css(".inventory_item_price"));
        List<Double> prices = priceElements.stream()
                .map(el -> Double.parseDouble(el.getText().replace("$", "")))
                .collect(Collectors.toList());

        List<Double> sortedPrices = prices.stream().sorted().collect(Collectors.toList());
        assertEquals(sortedPrices, prices, "Products should be sorted by price low to high");
    }
}