package com.demo.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.demo.utils.ApiUtils;
import org.junit.jupiter.api.extension.*;

import java.util.Optional;

public class ExtentJunit5Extension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, TestWatcher {

    private static ExtentReports extent;

    @Override
    public void beforeAll(ExtensionContext context) {
        extent = ExtentManager.getInstance();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String testName = context.getDisplayName();
        String className = context.getRequiredTestClass().getSimpleName();
        ExtentTest test = extent.createTest(className + " : " + testName);
        ExtentTestManager.setTest(test);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentTestManager.getTest().pass("Test passed");
        ExtentTestManager.removeTest();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.fail(cause);

//            // Attach last request/response captured by ApiUtils (see ApiUtils changes below)
//            String req = ApiUtils.getLastRequest();
//            String resp = ApiUtils.getLastResponse();

//            if (req != null && !req.isEmpty()) {
//                test.info("Request payload:");
//                test.info(MarkupHelper.createCodeBlock(req, CodeLanguage.JSON));
//            }
//            if (resp != null && !resp.isEmpty()) {
//                test.info("Response:");
//                test.info(MarkupHelper.createCodeBlock(resp, CodeLanguage.JSON));
//            }
        }
        ExtentTestManager.removeTest();
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        ExtentTest t = ExtentTestManager.getTest();
        if (t != null) t.skip("Test disabled: " + reason.orElse(""));
        ExtentTestManager.removeTest();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (extent != null) extent.flush();
    }
}
