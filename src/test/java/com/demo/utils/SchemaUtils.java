package com.demo.utils;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaUtils {

    public static void validateSchema(Response response, String objectSchema, String arraySchema) {
        String body = response.getBody().asString();

        if (body.trim().startsWith("[")) {
            // Array response
            response.then().body(matchesJsonSchemaInClasspath(arraySchema));
        } else {
            // Object response
            response.then().body(matchesJsonSchemaInClasspath(objectSchema));
        }
    }
}
