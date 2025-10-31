package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ReportManager;
import utils.ScreenShots;
import utils.DBConnection;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected Properties config;
    protected ExtentTest test;

    @BeforeSuite
    public void beforeSuite() {
        ReportManager.initReports();
    }

    @BeforeMethod
    public void setUp(Object[] data) {
        config = ConfigReader.getConfig();
        String browser = config.getProperty("browser");
        driver = DriverFactory.initDriver(browser);
        driver.get(config.getProperty("baseUrl"));

        String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
        test = ReportManager.createTest(testName);
        test.log(Status.INFO, "Starting test: " + testName);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = ScreenShots.captureScreenshot(driver, result.getName());
                test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
                if (screenshotPath != null)
                    test.addScreenCaptureFromPath(screenshotPath);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.log(Status.PASS, "Test Passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.log(Status.SKIP, "Test Skipped");
            }
            saveResultToDB(result);

        } catch (Exception e) {
            System.out.println("Error during teardown reporting: " + e.getMessage());
        } finally {
            DriverFactory.quitDriver();
            ReportManager.flushReports();
        }
    }

    // 🧠 Helper to save test result into MySQL
    private void saveResultToDB(ITestResult result) {
        String testName = result.getName();
        String status;

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                status = "PASS";
                break;
            case ITestResult.FAILURE:
                status = "FAIL";
                break;
            case ITestResult.SKIP:
                status = "SKIP";
                break;
            default:
                status = "UNKNOWN";
                break;
        }
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO test_results (test_name, status) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, testName);
            ps.setString(2, status);
            ps.executeUpdate();
            System.out.println("Test result saved to DB: " + testName + " - " + status);
        } catch (Exception e) {
            System.out.println("Failed to save result to DB: " + e.getMessage());
        }
    }
}
