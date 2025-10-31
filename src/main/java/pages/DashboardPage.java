package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By sortDropdown = By.className("product_sort_container");
    private By priceLabels = By.className("inventory_item_price");
    private By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private By cartIcon = By.className("shopping_cart_link");
    private By cartItems = By.className("cart_item");
    private By menuButton = By.id("react-burger-menu-btn");
    private By aboutLink = By.id("about_sidebar_link");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAtDashboard() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectSortOption(String optionText) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
        new Select(dropdown).selectByVisibleText(optionText);
    }

    public boolean arePricesSortedLowToHigh() {
        List<WebElement> priceElements = driver.findElements(priceLabels);
        List<Double> prices = new ArrayList<>();
        for (WebElement e : priceElements) {
            prices.add(Double.parseDouble(e.getText().replace("$", "")));
        }
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        return prices.equals(sortedPrices);
    }

    public void addLowestTwoItemsToCart() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (int i = 0; i < Math.min(2, buttons.size()); i++) {
            wait.until(ExpectedConditions.elementToBeClickable(buttons.get(i))).click();
        }
    }

    public void goToCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        wait.until(driver -> driver.findElement(By.className("shopping_cart_badge")).getText().equals("2"));
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public int getCartItemCount() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItems));
        return driver.findElements(cartItems).size();
    }

    public List<Double> getCartItemPrices() {
        List<WebElement> priceElements = driver.findElements(priceLabels);
        List<Double> prices = new ArrayList<>();
        for (WebElement e : priceElements) {
            try {
                prices.add(Double.parseDouble(e.getText().replace("$", "")));
            } catch (Exception ex) {
                System.out.println("Couldn't parse price: " + e.getText());
            }
        }
        return prices;
    }

    public double calculateTotalPrice() {
        List<Double> prices = getCartItemPrices();
        double total = 0.0;
        for (Double p : prices) {
            total += p;
        }
        return total;
    }

    public void openMenuAndSelectAbout() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink)).click();
    }

    public boolean clickFacebookIfPresent() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            WebElement fbLink = driver.findElement(By.partialLinkText("Facebook"));
            wait.until(ExpectedConditions.elementToBeClickable(fbLink)).click();
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Facebook link not found on page.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
