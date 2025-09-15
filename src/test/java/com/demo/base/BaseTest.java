package com.demo.base;

import com.demo.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);


    // Instead of repeating headers, base URI, authentication, query params, etc. in every test, you define them once in a request spec, and reuse it across tests.
    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;

//    ✅ Option 2 – Have Predefined ResponseSpecs
    protected static ResponseSpecification okResponseSpec;
    protected static ResponseSpecification createdResponseSpec;
    protected static ResponseSpecification deletedResponseSpec;
    protected static ResponseSpecification listResponseSpec;

    @BeforeAll
    static void setup(){

//        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
//        RestAssured.basePath = "/";     // optional

//        requestSpecification = new RequestSpecBuilder()
//                .setBaseUri("https://jsonplaceholder.typicode.com")
//                .setBasePath("/")
//                .setContentType("application/json")
//                .build();


        String baseURI = ConfigManager.getProperty("base.uri");
        String basePath = ConfigManager.getProperty("base.path");
        String contentType = ConfigManager.getProperty("default.content.type");

        requestSpecification = new RequestSpecBuilder()       // ✅ RequestSpecification
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setContentType(contentType)
                .build();

        responseSpecification = new ResponseSpecBuilder()     // ✅ ResponseSpecification
                .expectContentType("application/json")
                .expectResponseTime(lessThan(10000L))
                .build();

        okResponseSpec = new ResponseSpecBuilder()            // ✅ ResponseSpecification for Update/Ok resource
                .expectStatusCode(200)
//                .expectContentType("application/json")
                .expectBody(matchesJsonSchemaInClasspath("schemas/postSchema.json"))    // ✅ Single object schema
                .expectResponseTime(lessThan(10000L))
                .build();

        createdResponseSpec = new ResponseSpecBuilder()       // ✅ ResponseSpecification for created resource
                .expectStatusCode(201)
//                .expectContentType("application/json")
                .expectBody(matchesJsonSchemaInClasspath("schemas/postSchema.json"))    // ✅ Single object schema
                .expectResponseTime(lessThan(10000L))
                .build();

        deletedResponseSpec = new ResponseSpecBuilder()       // ✅ ResponseSpecification for Delete resource
                .expectStatusCode(anyOf(is(200), is(204)))
                .expectContentType("application/json")
                .expectResponseTime(lessThan(10000L))
                .build();

        listResponseSpec = new ResponseSpecBuilder()          // ✅ ResponseSpecification for Get All resources
                .expectStatusCode(200)
                .expectBody(matchesJsonSchemaInClasspath("schemas/postArraySchema.json"))   // ✅ Array schema
                .expectResponseTime(lessThan(10000L))
                .build();

//     👉 Note: So the key is:
//        Use postSchema.json for single resource (POST/PUT/GET by ID).
//        Use postArraySchema.json for collections (GET all).

    }

    /**
     * (Option 1 – Pass Expected Status Dynamically)
     * Helper method that builds one based on the status you expect
     * @param expectedStatusCode
     * @return
     */
    public static ResponseSpecification getResponseSpec(int expectedStatusCode) {

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .expectContentType("application/json")
                .expectResponseTime(lessThan(5000L))
                .build();
        return responseSpecification;
    }
}

