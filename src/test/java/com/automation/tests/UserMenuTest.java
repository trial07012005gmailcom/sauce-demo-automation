package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

public class UserMenuTest extends BaseTest {

    @Test
    public void testUserMenuFunctionality() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        // Open menu
        driver.findElement(By.id("react-burger-menu-btn")).click();

        // Wait for menu to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.css(".bm-menu-wrap")));

        // Verify menu items are visible
        assertTrue(driver.findElement(By.id("inventory_sidebar_link")).isDisplayed(),
                "All Items menu should be visible");
        assertTrue(driver.findElement(By.id("about_sidebar_link")).isDisplayed(),
                "About menu should be visible");
        assertTrue(driver.findElement(By.id("logout_sidebar_link")).isDisplayed(),
                "Logout menu should be visible");
        assertTrue(driver.findElement(By.id("reset_sidebar_link")).isDisplayed(),
                "Reset App State menu should be visible");

        // Test About link
        driver.findElement(By.id("about_sidebar_link")).click();

        // Verify redirection to Sauce Labs website
        wait.until(ExpectedConditions.urlContains("saucelabs"));
        assertTrue(driver.getCurrentUrl().contains("saucelabs.com"),
                "Should redirect to Sauce Labs website");
    }
}