package com.api.automation.tests;

import com.api.automation.utils.EnvironmentConfig;
import com.api.automation.utils.OctopusVariables;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected static EnvironmentConfig envConfig;
    
    @BeforeSuite
    public void suiteSetup() {
        envConfig = EnvironmentConfig.getInstance();
    }
    
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = OctopusVariables.getVariable("BASE_URL");
    }
}
