package com.demo.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class paramApiTest {

    @Test
    void queryParamRequestTest(){

        Response response =
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .queryParam("userId", 2)
                .when()
                .get("/posts");
//                .then()
//                .statusCode(200)
//                .body("size()", greaterThan(0));

        System.out.println("Response data : "+ response.asString());
    }
}
