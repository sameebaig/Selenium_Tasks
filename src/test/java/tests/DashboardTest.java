package tests;

import base.BaseTest;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.DashboardPage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.DBConnection;

public class DashboardTest extends BaseTest {

    @Test(priority = 0)
    public void sortAndValidateCartItems() {
        LoginPage login = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Login
        login.enterUsername(config.getProperty("username"));
        login.enterPassword(config.getProperty("password"));
        login.clickLogin();

        DashboardPage dash = new DashboardPage(driver);

        // Wait for dashboard to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        Assert.assertTrue(dash.isAtDashboard(), "Dashboard not loaded.");

        // Sort by low to high
        dash.selectSortOption("Price (low to high)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_price")));
        Assert.assertTrue(dash.arePricesSortedLowToHigh(), "Prices not sorted low to high.");

        // Add lowest two items
        dash.addLowestTwoItemsToCart();

        // Wait until cart shows "2"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        wait.until(driver -> driver.findElement(By.className("shopping_cart_badge")).getText().equals("2"));

        dash.goToCart();

        // Verify item count
        Assert.assertEquals(dash.getCartItemCount(), 2, "Cart should have 2 items.");

        // Verify prices inside the cart
        List<Double> cartPrices = dash.getCartItemPrices();
        Assert.assertTrue(cartPrices.size() == 2, "Cart should contain 2 price entries.");
        Assert.assertTrue(cartPrices.get(0) <= cartPrices.get(1), "Cart prices not sorted correctly.");

        // Verify total = sum of individual prices
        double total = dash.calculateTotalPrice();
        double expectedTotal = cartPrices.get(0) + cartPrices.get(1);
        Assert.assertEquals(total, expectedTotal, "Total price mismatch in cart.");
    }
    
    @Test(priority = 1)
    public void aboutPageAndFacebookCheck() {
        LoginPage login = new LoginPage(driver);
        login.enterUsername(config.getProperty("username"));
        login.enterPassword(config.getProperty("password"));
        login.clickLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.openMenuAndSelectAbout();

        // Wait until URL contains saucelabs.com
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("saucelabs.com"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucelabs.com"), "About page not opened.");

        //check for fb
        boolean fbFound = dashboard.clickFacebookIfPresent();
        if (fbFound) {
            for (String window : driver.getWindowHandles()) {
                driver.switchTo().window(window);
            }
            wait.until(ExpectedConditions.urlContains("facebook.com"));
            Assert.assertTrue(driver.getCurrentUrl().contains("facebook.com"), "Facebook url mismatch.");
        } else {
            System.out.println("Facebook link not found");
        }
     } 
}
