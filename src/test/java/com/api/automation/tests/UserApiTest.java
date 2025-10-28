package com.api.automation.tests;

import com.api.automation.utils.ConfigProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApiTest extends BaseTest {

    @Test
    public void testGetUsersList() {
        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .when().log().all()
            .get(ConfigProperties.USERS_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .extract().response();

        // Verify response contains users data
        Assert.assertNotNull(response.jsonPath().getString("data"));
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0);
        Assert.assertNotNull(response.jsonPath().getString("data[0].id"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].email"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].first_name"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].last_name"));
    }

    @Test
    public void testGetUsersWithPagination() {
        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .queryParam("page", 2)
            .queryParam("per_page", 3)
            .when().log().all()
            .get(ConfigProperties.USERS_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .extract().response();

        // Verify pagination data
        Assert.assertEquals(response.jsonPath().getInt("page"), 2);
        Assert.assertEquals(response.jsonPath().getInt("per_page"), 3);
        Assert.assertEquals(response.jsonPath().getList("data").size(), 3);
    }

    @Test
    public void testGetSingleUser() {
        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .pathParam("id", 2)
            .when().log().all()
            .get(ConfigProperties.SINGLE_USER_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .extract().response();

        // Verify single user data
        Assert.assertEquals(response.jsonPath().getInt("data.id"), 2);
        Assert.assertNotNull(response.jsonPath().getString("data.email"));
        Assert.assertNotNull(response.jsonPath().getString("data.first_name"));
        Assert.assertNotNull(response.jsonPath().getString("data.last_name"));
    }

    @Test
    public void testUserNotFound() {
        given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .pathParam("id", 23) // Non-existent user ID
            .when().log().all()
            .get(ConfigProperties.SINGLE_USER_ENDPOINT)
            .then().log().all()
            .statusCode(404);
    }

    @Test
    public void testCreateUser() {
        Map<String, String> userBody = new HashMap<>();
        userBody.put("name", "John Doe");
        userBody.put("job", "Software Tester");

        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .body(userBody)
            .when().log().all()
            .post(ConfigProperties.USERS_ENDPOINT)
            .then().log().all()
            .statusCode(201)
            .extract().response();

        // Verify created user data
        Assert.assertEquals(response.jsonPath().getString("name"), "John Doe");
        Assert.assertEquals(response.jsonPath().getString("job"), "Software Tester");
        Assert.assertNotNull(response.jsonPath().getString("id"));
        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
    }

    @Test
    public void testUpdateUser() {
        Map<String, String> updateBody = new HashMap<>();
        updateBody.put("name", "John Updated");
        updateBody.put("job", "Senior Tester");

        Response response = given()
            .contentType(ContentType.JSON)
            .header("x-api-key", ConfigProperties.API_KEY)
            .body(updateBody)
            .pathParam("id", 2)
            .when().log().all()
            .put(ConfigProperties.SINGLE_USER_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .extract().response();

        // Verify updated user data
        Assert.assertEquals(response.jsonPath().getString("name"), "John Updated");
        Assert.assertEquals(response.jsonPath().getString("job"), "Senior Tester");
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"));
    }
}
