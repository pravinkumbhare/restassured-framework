package com.demo.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.demo.config.ConfigManager;

public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String out = System.getProperty("user.dir") + "/target/extent-report.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(out);
            spark.config().setDocumentTitle("API Automation Report");
            spark.config().setReportName("RestAssured API Tests");
            spark.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // useful system info
            extent.setSystemInfo("Environment", System.getProperty("env", "local"));
            extent.setSystemInfo("BaseURI", ConfigManager.getProperty("base.uri"));
        }
        return extent;
    }
}
