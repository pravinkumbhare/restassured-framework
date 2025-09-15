package com.demo.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class postApiTest {

    @Test
    public void getPostApiTest() {
        given()
                .log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", notNullValue());
    }

    @Test
    public void postRequestTest() {
        String payload = "{\n" +
                "    \"userId\": 111,\n" +
                "    \"title\": \"New title provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"New testing data.................\"\n" +
                "}";

        given()
                .log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .body("userId", equalTo(111))
                .body("title", equalTo("New title provident occaecati excepturi optio reprehenderit"))
                .body("id", notNullValue());
    }

    @Test
    void queryParamRequestTest() {
        given()
                .log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .queryParam("userId", 2)
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(2)));
    }

    @Test
    void putRequestTest() {
        String payload = "{\n" +
                "    \"userId\": 707,\n" +
                "    \"title\": \"Updated title provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"Updated testing data.................\"\n" +
                "}";

        given()
                .log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .put("/posts/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("userId", equalTo(707))
                .body("title", containsString("Updated title"));
    }

    @Test
    void deleteRequestTest() {
        given()
                .log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .delete("/posts/1")
                .then()
                .log().all()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    public void logAllTest() {
        given()
                .log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("title", notNullValue());
    }
}
