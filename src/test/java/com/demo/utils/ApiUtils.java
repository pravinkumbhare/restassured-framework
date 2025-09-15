package com.demo.utils;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.demo.base.BaseTest;
import com.demo.reports.ExtentTestManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiUtils {

    private static final Logger logger = LogManager.getLogger(ApiUtils.class);

    // ✅ POST
    public static Response post(RequestSpecification requestSpecification, String endpoint, String payload){
        return given().spec(requestSpecification).body(payload).when().post(endpoint).then().extract().response();
    }

    // ✅ PUT
    public static Response put(RequestSpecification requestSpecification, String endpoint, String payload){
        return given().spec(requestSpecification).body(payload).when().put(endpoint).then().extract().response();
    }

    // ✅ DELETE
    public static Response delete(RequestSpecification requestSpecification, String endpoint){
        return given().spec(requestSpecification).delete(endpoint).then().extract().response();
    }


    // ✅ GET
    public static Response get(RequestSpecification requestSpecification, String endpoint){
        return given().spec(requestSpecification).when().get(endpoint).then().extract().response();
    }


    /**
     * Overloaded Reusable methods for POST request
     * @param requestSpecification
     * @param endpoint
     * @param body
     * @return
     */
    public static Response post(RequestSpecification requestSpecification, String endpoint, Object body){

        logger.info("POST Request -> Endpoint: {}, Payload: {}", endpoint, body);

        String requestPayload = String.valueOf(body);

        Response response =
                        given()
                        .spec(requestSpecification)
                        .body(body)
                        .when()
                        .post(endpoint)
                        .then()

//                .spec(BaseTest.getResponseSpec(201))          <== Option 3
                        .extract()
                        .response();

        logToExtent(requestPayload, response);

        logger.info("Response Status: {}, Body: {}", response.statusCode(), response.asString());

        return response;
    }

    /**
     * Overloaded Reusable methods for PUT request
     * @param requestSpecification
     * @param endpoint
     * @param body
     * @return
     */
    public static Response put(RequestSpecification requestSpecification, String endpoint, Object body){

        logger.info("PUT Request -> Endpoint: {}, Payload: {}", endpoint, body);

        String requestPayload = String.valueOf(body);

        Response response =
                given()
                    .spec(requestSpecification)
                    .body(body)
                    .when()
                    .put(endpoint)
                    .then()

    //                .spec(BaseTest.getResponseSpec(200))
                    .extract()
                    .response();

        logger.info("Response Status: {}, Body: {}", response.statusCode(), response.asString());

        logToExtent(requestPayload, response);

        return response;
    }


    /**
     *  GET (with optional query params)
     * @param reqSpec
     * @param endpoint
     * @param queryParams
     * @return
     */
    public static Response get(RequestSpecification reqSpec, String endpoint, Map<String, ?> queryParams) {

        logger.info("GET Request -> Endpoint: {}", endpoint);
        Response response =
                 given()
                    .spec(reqSpec)
                    .queryParams(queryParams != null ? queryParams : Map.of())
                    .when()
                    .get(endpoint)
                    .then()
                    .extract()
                    .response();

        logger.info("Response Status: {}", response.statusCode());
        logToExtent("GET " + endpoint, response);

        return response;
    }


    /**
     * Common helper for logging
     * @param requestPayload
     * @param response
     */
    private static void logToExtent(String requestPayload, Response response) {
        if (ExtentTestManager.getTest() != null) {
            // Request
            ExtentTestManager.getTest()
                    .info("Request:")
                    .info(MarkupHelper.createCodeBlock(prettyPrintJson(requestPayload), CodeLanguage.JSON));

            // Response
            ExtentTestManager.getTest()
                    .info("Response Body:")
                    .info(MarkupHelper.createCodeBlock(prettyPrintJson(response.asString()), CodeLanguage.JSON));

            // Status Code
            ExtentTestManager.getTest()
                    .info("Status Code: " + response.getStatusCode());

            // Response Time
            ExtentTestManager.getTest()
                    .info("Response Time: " + response.getTime() + " ms");

            // Headers
            ExtentTestManager.getTest()
                    .info("Headers:")
                    .info(MarkupHelper.createCodeBlock(response.getHeaders().toString(), CodeLanguage.JSON));
        }
    }

    private static String prettyPrintJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            Object jsonObj = mapper.readValue(json, Object.class);
            return writer.writeValueAsString(jsonObj);
        } catch (Exception e) {
            // fallback: return as-is if not valid JSON
            return json;
        }
    }

}
