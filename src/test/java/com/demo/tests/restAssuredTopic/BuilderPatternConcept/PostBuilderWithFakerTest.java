package com.demo.tests.restAssuredTopic.BuilderPatternConcept;

import com.demo.base.BaseTest;
import com.demo.models.Post;
import com.demo.utils.ApiUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PostBuilderWithFakerTest extends BaseTest {


//    ✅ Benefits
//    Dynamic by default → thanks to Faker.
//    Override only what you want → flexible.
//    Readable tests → no huge constructors or boilerplate.
    @Test
    public void testPostBuilderWithFaker(){

        Post postRequest = new PostBuilderWithFakerAndFactory()
                .withRandomData()
                .withUserId(999) // override only if needed
                .build();

        Response response = ApiUtils.post(requestSpecification, "/posts", postRequest);

        response.then()
                .spec(createdResponseSpec)
                .statusCode(201);   // not needed

        System.out.println("Created Post => " + postRequest.getTitle());
    }

    @ParameterizedTest
    @Tag("BuilderFakerTest")
    @CsvSource({
            "999, Custom Title 1",
            "888, Custom Title 2"
    })
    public void postBuilderWithFakerParameterizedTest(int userId, String title){

        Post postRequest = new PostBuilderWithFakerAndFactory()
                .withRandomData()
                .withUserId(userId) // override only if needed
                .withTitle(title)
                .build();

        Response response = ApiUtils.post(requestSpecification, "/posts", postRequest);

        response.then()
                .spec(createdResponseSpec)
                .statusCode(201);   // not needed

        System.out.println("Created Post => " + postRequest.getTitle());
    }


}
