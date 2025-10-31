package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initReports() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Test Results");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
    }
    public static ExtentReports getExtent() {
        return extent;
    }
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        return extentTest;
    }
    public static ExtentTest getTest() {
        return test.get();
    }
}
