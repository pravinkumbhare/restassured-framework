package com.demo.tests.restAssuredTopic;

import com.demo.models.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FixturesExample {

    static Post sharedPost;

    @BeforeAll
    static void globalSetup(){
        System.out.println("Runs once before all tests – DB connection / env setup");
    }

    @BeforeEach
    void setUp(){

        sharedPost = new Post();
        sharedPost.setUserId(1234);
        sharedPost.setTitle("Default Title");
        sharedPost.setBody("Default Body");
    }

//  👉 Benefit: Consistent baseline data, less repetition.
    @Test
    public void test1(){
        System.out.println("UserId is : "+ sharedPost.getUserId());
        System.out.println("Title is : "+ sharedPost.getTitle());
        System.out.println("Body is : "+ sharedPost.getBody());
    }

    @Test
    public void test2(){
        System.out.println("UserId is : "+ sharedPost.getUserId());
        System.out.println("Title is : "+ sharedPost.getTitle());
        System.out.println("Body is : "+ sharedPost.getBody());
    }
}
