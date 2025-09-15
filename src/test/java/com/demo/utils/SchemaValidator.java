package com.demo.utils;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidator {

    public static void validate(Response response, String schemaPath) {
        response.then()
                .body(matchesJsonSchemaInClasspath(schemaPath))
                .log().all();
    }
}
