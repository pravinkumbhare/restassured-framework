package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.models.Post;
import com.demo.tests.restAssuredTopic.BuilderPatternConcept.PostBuilderWithFakerAndFactory;
import com.demo.utils.ApiUtils;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

// Replace @CsvSource with a @MethodSource so we can programmatically generate test data
// using both fixed values + faker.

@Epic("API Automation Framework")
@Feature("POST APIs")
@ExtendWith({AllureJunit5.class, com.demo.reports.ExtentJunit5Extension.class})
public class CRUDUsingBuilderFakerFactoryMethodSource extends BaseTest {

    private static final Faker faker = new Faker();

//    // 🔹 Data provider using Builder + Faker
//    static Stream<Post> postDataProvider() {
//        return Stream.of(
//                new PostBuilderWithFakerAndFactory().withRandomData().build(),
//                new PostBuilderWithFakerAndFactory().withRandomData().build(),
//                new PostBuilderWithFakerAndFactory().withUserId(999).withRandomData().build()
//        );
//    }

    // --- Data provider for create tests ---
    static Stream<Arguments> createPostData() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(1234, "Fixed Title A", faker.lorem().sentence()),
                org.junit.jupiter.params.provider.Arguments.of(5678, faker.book().title(), "Fixed Body B"),
                org.junit.jupiter.params.provider.Arguments.of(9999, "Hybrid Title " + faker.number().digits(3), faker.lorem().paragraph())
        );
    }

    @ParameterizedTest
    @Tag("UsingBuilderFakerFactoryMethodSource")
    @MethodSource("createPostData")
    @Story("Create test data using Builder Pattern, Factory, Faker and MethodSource")
    @Description("Verify that a post can be created using Builder Pattern, Factory, Faker and MethodSource")
    @Severity(SeverityLevel.CRITICAL)
    void createPostFromBuilder(int userId, String title, String body) {

        Post requestData = new PostBuilderWithFakerAndFactory()
                .withUserId(userId)
                .withTitle(title)
                .withBody(body)
                .build();

        Response response = ApiUtils.post(requestSpecification, "/posts", requestData);

        Post responseData = response.then()
                .spec(createdResponseSpec)
                .extract()
                .as(Post.class);

        Assertions.assertEquals(responseData.getUserId(), requestData.getUserId());
        Assertions.assertEquals(responseData.getTitle(), requestData.getTitle());
        Assertions.assertEquals(responseData.getBody(), requestData.getBody());
    }

    // --- Data provider for update tests ---
    static Stream<org.junit.jupiter.params.provider.Arguments> updatePostData() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(1111, faker.book().title(), "Update Body 1"),
                org.junit.jupiter.params.provider.Arguments.of(2222, "Fixed Update Title", faker.lorem().sentence()),
                org.junit.jupiter.params.provider.Arguments.of(3333, "Mix-" + faker.animal().name(), "Hybrid Body " + faker.number().randomDigit())
        );
    }

    @ParameterizedTest
    @Tag("UsingBuilderFakerFactoryMethodSource")
    @MethodSource("updatePostData")
    @Story("Update test data using Builder Pattern, Factory, Faker and MethodSource")
    @Description("Verify that a post can be updated using Builder Pattern, Factory, Faker and MethodSource")
    @Severity(SeverityLevel.NORMAL)
    public void updatePostFromPojo(int userId, String title, String body) {

        Post updateRequestData = new PostBuilderWithFakerAndFactory()
                .withUserId(userId)
                .withTitle(title)
                .withBody(body)
                .build();

        Response response = ApiUtils.put(requestSpecification, "/posts/1", updateRequestData);

        Post updateResponseData = response.then()
                .spec(okResponseSpec)
                .extract()
                .as(Post.class);

        Assertions.assertEquals(updateRequestData.getUserId(), updateResponseData.getUserId());
        Assertions.assertEquals(updateRequestData.getTitle(), updateResponseData.getTitle());
        Assertions.assertEquals(updateRequestData.getBody(), updateResponseData.getBody());
    }


}
