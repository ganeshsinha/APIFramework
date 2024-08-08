package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentSparkReporter htmlReporter;

    public static void initReports() {
        if (extent == null) {  // Check to avoid reinitialization
            htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/target/ExtentReport.html");
            htmlReporter.config().setDocumentTitle("API Test Report");
            htmlReporter.config().setReportName("API Test Automation Report");
            htmlReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
    }

    public static void createTest(String testName) {
        if (extent == null) {
            initReports();  // Ensure extent is initialized
        }
        test = extent.createTest(testName);
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
