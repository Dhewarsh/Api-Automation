package com.api.automation.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import com.api.automation.utils.ConfigProperties;

public class AuthenticationApiTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        Map<String, String> loginBody = new HashMap<>();
        loginBody.put("email", "eve.holt@reqres.in");
        loginBody.put("password", "cityslicka");

        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .body(loginBody)
            .when().log().all()
            .post(ConfigProperties.LOGIN_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .extract().response();

        // Verify login response
        Assert.assertNotNull(response.jsonPath().getString("token"));
    }

    @Test
    public void testUnsuccessfulLogin() {
        Map<String, String> loginBody = new HashMap<>();
        loginBody.put("email", "peter@klaven");
        // Missing password field

        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .body(loginBody)
            .when().log().all()
            .post(ConfigProperties.LOGIN_ENDPOINT)
            .then().log().all()
            .statusCode(400)
            .extract().response();

        // Verify error response
        Assert.assertEquals(response.jsonPath().getString("error"), "Missing password");
    }
}
