package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.models.Post;
import com.demo.tests.restAssuredTopic.BuilderPatternConcept.PostBuilderWithFakerAndFactory;
import com.demo.utils.ApiUtils;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

//✅ Refactored CRUDUsingPOJO with Builder + Faker + Parameterized

@Epic("API Automation Framework")
@Feature("POST APIs")
@ExtendWith({AllureJunit5.class, com.demo.reports.ExtentJunit5Extension.class})
public class CRUDUsingBuilderFakerFactoryParameterized extends BaseTest {

    @ParameterizedTest
    @Tag("UsingBuilderFakerFactoryParameterized")
    @CsvSource({
            "1234, Title One, Body One",
            "5678, Title Two, Body Two",
            "3465, Title Three, Body Three"
    })
    @Story("Create test data using Builder Pattern, Factory, Faker and Parameterized")
    @Description("Verify that a post can be created using Builder Pattern, Factory, Faker and Parameterized")
    @Severity(SeverityLevel.CRITICAL)
    void createPostFromBuilder(int userId, String title, String body) {

        Post requestData = new PostBuilderWithFakerAndFactory()
                .withRandomData()   // Faker gives you random realistic values
                .withUserId(userId) // fixed from @CsvSource
                // .withTitle(title) // intentionally skipped → faker title will be used
                .withBody(body)     // controlled from @CsvSource
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

    @ParameterizedTest
    @Tag("UsingBuilderFakerFactoryParameterized")
    @CsvSource({
            "1111, Title One, Body One",
            "2222, Title Two, Body Two",
            "3333, Title Three, Body Three"
    })
    @Story("Update test data using Builder Pattern, Factory, Faker and Parameterized")
    @Description("Verify that a post can be created using Builder Pattern, Factory, Faker and Parameterized")
    @Severity(SeverityLevel.NORMAL)
    public void updatePostFromPojo(int userId, String title, String body) {

        // Serialization from POJO -> Json
        Post updateRequestData = new PostBuilderWithFakerAndFactory()
        //      .withRandomData() // skipped → no randomness
                .withUserId(userId)
                // .withTitle(title) // faker will generate one
                .withBody(body)
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
