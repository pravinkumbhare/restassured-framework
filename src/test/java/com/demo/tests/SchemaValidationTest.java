package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.utils.ApiUtils;
import com.demo.utils.SchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class SchemaValidationTest extends BaseTest {

    @Test
    public void validatePostSchema(){

        Response response = ApiUtils.get(requestSpecification, "/posts/1");

        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/postSchema.json"))   // ✅ Single object schema
                .log().all();

        // [ OR ]
        SchemaValidator.validate(response, "schemas/postSchema.json");    // ✅ Single object schema
    }

    @Test
    public void validatePostSchema_AfterCreate() {
        // Arrange
        Map<String, Object> payload = Map.of(
                "userId", 1,
                "title", "foo",
                "body", "bar"
        );

        // Act
        Response response = ApiUtils.post(requestSpecification, "/posts", payload);

        // Assert
        response.then()
                .statusCode(201)
                .body("title", equalTo("foo"));

        // Schema Validation
        SchemaValidator.validate(response, "schemas/postSchema.json");      // ✅ Single object schema
    }

    @Test
    public void validatePostsArraySchema_OnGet() {
        // Act
        Map<String, Object> queryParam = Map.of("userId", 1);
        Response response = ApiUtils.get(requestSpecification, "/posts", queryParam);

        // Assert
        response.then()
                .statusCode(200);

        // Schema Validation
        SchemaValidator.validate(response, "schemas/postArraySchema.json");         // ✅ Array schema
    }
}


//👉 This gives you three layers of validation now:
//    Status Code / Headers → via ResponseSpec.
//    Payload Values → via Hamcrest matchers.
//    Structure → via Schema Validation.
