package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.config.ConfigManager;
import com.demo.utils.ApiUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class CRUDApiTest extends BaseTest {

    @Test
    public void getPostApiTest() {

        Response response =
                ApiUtils.get(requestSpecification, "/posts/1");

        response.then()
//                .statusCode(200)
                .spec(responseSpecification)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }


    @Test
    public void postRequestTest(){

        String payload = "{\n" +
                "    \"userId\": 111,\n" +
                "    \"title\": \"New title provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"New testing data.................\"\n" +
                "}";

       Response response =
               ApiUtils.post(requestSpecification, "/posts", payload);

        response.then()
                .statusCode(201)
                .body("userId", equalTo(111));
    }


    @Test
    void queryParamRequestTest(){

        Response response =
                given()
                        .log().all()
//                        .baseUri("https://jsonplaceholder.typicode.com")
                        .spec(requestSpecification)
                        .queryParam("userId", 2)
                        .when()
                        .log().all()
                        .get("/posts");

        response
                .then()
//                .statusCode(200)
                .spec(responseSpecification)
                .body("size()", greaterThan(0));

        System.out.println("Response data : "+ response.asString());
    }

    @Test
    void putRequestTest(){

        String payload = "{\n" +
                "    \"userId\": 707,\n" +
                "    \"title\": \"Updated title provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"Updated testing data.................\"\n" +
                "}";

        Response response =
                ApiUtils.put(requestSpecification, "/posts/1", payload);

        response.then()
                .spec(responseSpecification)
                .body("title", containsString("Updated title"));
    }

    @Test
    void deleteRequestTest(){

       Response response =
               ApiUtils.delete(requestSpecification, "/posts/1");

        response.then()
                .statusCode(anyOf(is(200), is(204)));

        System.out.println("Delete Request Response data : "+ response.asString());
    }


    @Test
    public void log_all_Test() {

        Response response =
                given()
                        .log().all()
//                        .baseUri("https://jsonplaceholder.typicode.com")
                        .spec(requestSpecification)
                        .when()
                        .log().all()
                        .get("/posts/1");

        response.then()
//                .statusCode(200)
                .spec(responseSpecification)
                .body("userId", equalTo(1))
                .body("title", notNullValue());
    }
}
