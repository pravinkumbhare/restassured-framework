package com.demo.tests;

import com.demo.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetApiTest extends BaseTest {


    @Test
    public void getPostApiTest() {

        Response response =
        given()
                .log().all()
//                .baseUri("https://jsonplaceholder.typicode.com")
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("/posts/1");

//        System.out.println("Response data : "+ response.asString());
//        System.out.println("Response title : "+ response.jsonPath().getString("title"));
//        System.out.println("Response body : "+ response.jsonPath().getString("body"));
//        System.out.println("Response userId : "+ response.jsonPath().getString("userId"));

        response.then()
//                .statusCode(200)
                .spec(responseSpecification)
                .body("userId", equalTo(1))
                .body("title", notNullValue());

    }


    @Test
    public void postRequestTest(){

        String payload = "{\n" +
                "    \"userId\": 111,\n" +
                "    \"title\": \"New title provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"New testing data.................\"\n" +
                "}";

        Response response = RestAssured.given()
                .log().all()
//                .baseUri("https://jsonplaceholder.typicode.com")
//                .header("Content-Type", "application/json")
                .spec(requestSpecification)
                .body(payload)
                .when()
                .log().all()
                .post("/posts");

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

//        Response response =
        given()
                .log().all()
//                .baseUri("https://jsonplaceholder.typicode.com")
//                .header("Content-Type", "application/json")
                .spec(requestSpecification)
                .body(payload)
                .when()
                .log().all()
                .put("/posts/1")
                .then()
//                .statusCode(200)
                .spec(responseSpecification)
                .body("title", containsString("Updated title"));

//        System.out.println("Put Request Response data : "+ response.asString());
    }

    @Test
    void deleteRequestTest(){

        Response response =
        given()
                .log().all()
//                .baseUri("https://jsonplaceholder.typicode.com")
                .spec(requestSpecification)
                .when()
                .log().all()
                .delete("/posts/1");

        response.then().statusCode(anyOf(is(200), is(204)));

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
