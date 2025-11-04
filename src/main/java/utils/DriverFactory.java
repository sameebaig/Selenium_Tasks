package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver initDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            throw new IllegalArgumentException("Browser is empty");
        }
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                File edgeDriver = new File("C:\\Users\\samee.baig_venturedi\\Downloads\\edgedriver_win64\\msedgedriver.exe");
                if (edgeDriver.exists()) {
                    System.setProperty("webdriver.edge.driver", edgeDriver.getAbsolutePath());
                    driver = new EdgeDriver();
                } else {
                    try {
                        WebDriverManager.edgedriver().setup();
                        driver = new EdgeDriver();
                    } catch (Exception e) {
                        throw new RuntimeException(
                            "Failed to setup EdgeDriver" + edgeDriver.getAbsolutePath(), e);}
                    }
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        return driver;
    }
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
