package com.demo.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.*;

@Epic("Petstore API")
@Feature("User Tests")
@ExtendWith(AllureJunit5.class)   // ✅ This ensures Allure hooks into JUnit 5
public class SampleAllureTest {

    @Test
    @Description("Verify that Google homepage is accessible")
    @Story("Check external site availability")
    void testGoogle() {
        logStep("Sending GET request to Google homepage");

        given()
                .when().get("https://www.google.com")
                .then().statusCode(200);

        logStep("Verified response status is 200 OK");
    }

    @Step("Custom step for logging: {message}")
    public void logStep(String message) {
        // Any step can be logged inside Allure reports
        System.out.println("Step: " + message);
    }
}


