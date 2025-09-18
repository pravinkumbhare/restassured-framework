package com.demo.tests.restAssuredTopic;

import com.demo.base.BaseTest;
import com.demo.models.Post;
import com.demo.utils.ApiUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

//Instead of building objects inline, you create reusable factory methods.
public class FactoryMethodExample extends BaseTest {

    public static Post createDefaultPost(){

        Post post = new Post();
        post.setUserId(777);
        post.setTitle("Default Title");
        post.setBody("Default Body");

        return post;
    }

    public static Post createCustomPost(int userId, String title, String body){
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);
        return post;
    }

//    👉 Benefit: Centralized way to create test data.
    @Test
    public void factoryMethodTest(){

        Post requestPost = FactoryMethodExample.createCustomPost(9801, "Custom_Title", "Custom_Body");
        System.out.println(requestPost.getTitle());

        Response response = ApiUtils.post(requestSpecification, "/posts", requestPost);
        response.then()
                .spec(createdResponseSpec);
    }

}