package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.*;

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static ExtentTest test;
    static {
        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }
    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("test passed" + result.getName());
    }
    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("test failed" + result.getThrowable());
    }
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
