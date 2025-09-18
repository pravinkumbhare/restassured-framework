package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.models.Post;
import com.demo.utils.ApiUtils;
import com.demo.utils.TestDataLoader;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


@Epic("API Automation Framework")
@Feature("POST APIs")
@ExtendWith({AllureJunit5.class, com.demo.reports.ExtentJunit5Extension.class})
public class CRUDUsingPOJO extends BaseTest {

//    Approach 1 not recommended
//    @ParameterizedTest
//    @CsvSource({
//            "1234, Title One, Body One",
//            "5678, Title Two, Body Two"
//    })
    @Test
    @Story("Create POST using POJO payload")
    @Description("Verify that a post can be created using POJO serialization")
    @Severity(SeverityLevel.CRITICAL)
    void createPostParameterized(/*int userId, String title, String body*/) {

        Faker faker = new Faker();
        //Build POJO Request
        Post requestPost = new Post();
        requestPost.setUserId(faker.number().numberBetween(100,9999));
        requestPost.setTitle("Dynamic Title: "+ faker.book().title());
        requestPost.setBody("Dynamic Body: "+ faker.lorem().sentence());

        Response response = ApiUtils.post(requestSpecification, "/posts", requestPost);

        Post responsePost = response.then()
                .spec(createdResponseSpec)
                .extract()
                .as(Post.class);

        // Assertion
        Assertions.assertEquals(requestPost.getUserId(), responsePost.getUserId());
        Assertions.assertEquals(requestPost.getTitle(), responsePost.getTitle());
        Assertions.assertEquals(requestPost.getBody(), responsePost.getBody());
    }


    // Approach 2
//    So the flow becomes:
//    Load post.json → gives you a valid POJO object.
//    Override fields → inject dynamic values (so each test run is unique).
//    Send request with this final requestPost.
//    ✅ Why this is useful
//    post.json acts as a blueprint (ensures required fields are present).
//    Faker adds randomness (avoids duplicate test data, makes test more realistic).
//    You don’t need to hardcode values in the test → clean & maintainable.
//    This becomes scalable when you add multiple JSON files like:
//    createPost.json
//    updatePost.json
//    invalidPost.json
//    ✅ Best Approach for Framework:
//    👉 Hybrid (External + Faker) because:
//    You can keep baseline values in JSON (easy maintenance).
//    Add uniqueness with Faker/UUID (avoids duplication issues).
    @Test
    @Story("Create test data using Hybrid / Dynamic Approach")
    @Description("Verify that a post can be created using Dynamic Approach")
    @Severity(SeverityLevel.CRITICAL)
    void createPostFromHybridApproach() {

        // Step 1: You load static data from post.json and run the test with it.
        // Load POJO from JSON
        Post requestPost = TestDataLoader.loadData("createPost.json", Post.class);

        // Step 2: And then add Some Dynamic Data (Hybrid Approach)
        Faker faker = new Faker();
        requestPost.setUserId(faker.number().numberBetween(100, 1000));
        requestPost.setTitle(faker.book().title());
        requestPost.setBody(faker.lorem().sentence());

        Response response = ApiUtils.post(requestSpecification, "/posts", requestPost);

        Post responsePost = response.then()
                .spec(createdResponseSpec)
                .extract()
                .as(Post.class);

        // Assertions
        Assertions.assertEquals(requestPost.getUserId(), responsePost.getUserId());
        Assertions.assertEquals(requestPost.getTitle(), responsePost.getTitle());
        Assertions.assertEquals(requestPost.getBody(), responsePost.getBody());

    }

    @Test
    @Story("Update POST using POJO payload")
    @Description("Verify that a post can be updated using POJO serialization")
    @Severity(SeverityLevel.NORMAL)
    public void updatePostFromPojo() {
        // Build POJO request
        Post updatePost = new Post();
        updatePost.setUserId(777);
        updatePost.setTitle("Updated title from POJO");
        updatePost.setBody("Updated payload is built using POJO class.");

        // Send PUT request
        Response response = ApiUtils.put(requestSpecification, "/posts/1", updatePost);

        // Deserialize JSON response → POJO
        Post responsePost = response.then()
                .spec(okResponseSpec)
                .extract()
                .as(Post.class);

        // Assertions
        org.junit.jupiter.api.Assertions.assertEquals(updatePost.getUserId(), responsePost.getUserId());
        org.junit.jupiter.api.Assertions.assertEquals(updatePost.getTitle(), responsePost.getTitle());
        org.junit.jupiter.api.Assertions.assertEquals(updatePost.getBody(), responsePost.getBody());
    }


}
