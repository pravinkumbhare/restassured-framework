package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.models.Post;
import com.demo.tests.restAssuredTopic.BuilderPatternConcept.PostBuilderWithFakerAndFactory;
import com.demo.utils.ApiUtils;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

//✅ Refactored CRUDUsingPOJO with Builder + Faker
//Builder + Faker → Best for realistic/randomized test data (less brittle).

@Epic("API Automation Framework")
@Feature("POST APIs")
@ExtendWith({AllureJunit5.class, com.demo.reports.ExtentJunit5Extension.class})
public class CRUDUsingBuilderFakerFactory extends BaseTest {

    @Test
    @Story("Create test data using Builder Pattern, Factory and Faker")
    @Description("Verify that a post can be created using Builder Pattern, Factory and Faker")
    @Severity(SeverityLevel.CRITICAL)
    void createPostFromBuilder() {

        Post requestData = new PostBuilderWithFakerAndFactory()
                .withRandomData()
                .withUserId(777)
                .build();

        Response response = ApiUtils.post(requestSpecification, "/posts", requestData);

        Post responseData = response.then()
                .spec(createdResponseSpec)
                .extract()
                .as(Post.class);

        // Assertions
        Assertions.assertEquals(responseData.getUserId(), requestData.getUserId());
        Assertions.assertEquals(responseData.getTitle(), requestData.getTitle());
        Assertions.assertEquals(responseData.getBody(), requestData.getBody());
    }

    @Test
    @Story("Update POST using POJO payload")
    @Description("Verify that a post can be updated using POJO serialization")
    @Severity(SeverityLevel.NORMAL)
    public void updatePostFromPojo() {

        // Serialization from POJO -> Json
        Post updateRequestData = new PostBuilderWithFakerAndFactory()
                .withRandomData()
                .withUserId(777) // this value will be same remaining 2 fields value will take random from Faker
                .build();

        // Send PUT request
        Response response = ApiUtils.put(requestSpecification, "/posts/1", updateRequestData);

        // Deserialize JSON response → POJO
        Post updateResponseData = response.then()
                        .spec(okResponseSpec)
                                .extract()
                                        .as(Post.class);

        Assertions.assertEquals(updateRequestData.getUserId(), updateResponseData.getUserId());
        Assertions.assertEquals(updateRequestData.getTitle(), updateResponseData.getTitle());
        Assertions.assertEquals(updateRequestData.getBody(), updateResponseData.getBody());
    }

}
