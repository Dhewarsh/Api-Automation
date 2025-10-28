package com.api.automation.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.Date;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
        System.out.println("Failure Details: " + result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Execution Completed at: " + new Date());
        System.out.println("Total Tests: " + context.getAllTestMethods().length);
        System.out.println("Passed Tests: " + context.getPassedTests().size());
        System.out.println("Failed Tests: " + context.getFailedTests().size());
    }
}
