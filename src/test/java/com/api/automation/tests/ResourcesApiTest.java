package com.api.automation.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import com.api.automation.utils.ConfigProperties;

public class ResourcesApiTest extends BaseTest {

    @Test
    public void testGetResourcesList() {
        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .when().log().all()
            .get(ConfigProperties.RESOURCES_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .extract().response();

        // Verify response contains resources data
        Assert.assertNotNull(response.jsonPath().getString("data"));
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0);
        Assert.assertNotNull(response.jsonPath().getString("data[0].id"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].name"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].year"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].color"));
    }

    @Test
    public void testGetSingleResource() {
        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .pathParam("id", 2)
            .when().log().all()
            .get(ConfigProperties.SINGLE_RESOURCE_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .extract().response();

        // Verify single resource data
        Assert.assertEquals(response.jsonPath().getInt("data.id"), 2);
        Assert.assertNotNull(response.jsonPath().getString("data.name"));
        Assert.assertNotNull(response.jsonPath().getString("data.year"));
        Assert.assertNotNull(response.jsonPath().getString("data.color"));
        Assert.assertNotNull(response.jsonPath().getString("data.pantone_value"));
    }

    @Test
    public void testResourceNotFound() {
        given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .pathParam("id", 23) // Non-existent resource ID
            .when().log().all()
            .get(ConfigProperties.SINGLE_RESOURCE_ENDPOINT)
            .then().log().all()
            .statusCode(404);
    }
}
