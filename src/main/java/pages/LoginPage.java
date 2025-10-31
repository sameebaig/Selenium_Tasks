package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By usernameField = By.id("user-name"); //username
    private By passwordField = By.id("password");  //password
    private By loginButton = By.id("login-button"); //loginbutton
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
