package com.demo.tests.restAssuredTopic.BuilderPatternConcept;

import com.demo.base.BaseTest;
import com.demo.models.Post;
import com.demo.utils.ApiUtils;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class PostBuilderTest extends BaseTest {

    @Test
    public void postBuilderTest(){

//      👉 Benefit: Readable, flexible, avoids big constructors.
        Post postRequest = new PostBuilder()
                .withUserId(12345)
                .withTitle("Title with Buliler Pattern")
//                .withBody("Body with Builder Pattern")   // we can use only required fields by using builder pattern
                .build();

        Response response = ApiUtils.post(requestSpecification, "/posts", postRequest);

        response.then()
                .spec(responseSpecification)
                .statusCode(201);
    }

    @Test
    public void postBuilderWithFakerTest(){

        Faker faker = new Faker();

        Post postRequest = new PostBuilder()
                .withUserId(faker.number().numberBetween(100, 999))
                .withTitle(faker.book().title())
                .withBody(faker.lorem().sentence())   // we can use only required fields by using builder pattern
                .build();

        Response response = ApiUtils.post(requestSpecification, "/posts", postRequest);

        response.then()
                .spec(responseSpecification)
                .statusCode(201);
    }
}
