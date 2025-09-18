package com.demo.tests.restAssuredTopic.BuilderPatternConcept;

import com.demo.models.Post;
import com.github.javafaker.Faker;

public class PostBuilderWithFakerAndFactory {

    private int userId = 100;
    private String title = "Default Builder Title";
    private String body = "Default Builder Body";

    public PostBuilderWithFakerAndFactory withUserId(int userId){
        this.userId = userId;
        return this;
    }

    public PostBuilderWithFakerAndFactory withTitle(String title){
        this.title = title;
        return this;
    }

    public PostBuilderWithFakerAndFactory withBody(String body){
        this.body = body;
        return this;
    }

    private static final Faker faker = new Faker();

    public PostBuilderWithFakerAndFactory withRandomData(){

        this.userId = faker.number().numberBetween(1, 5000);
        this.title = "Title: " + faker.book().title();
        this.body = faker.lorem().paragraph();
        return this;
    }

    public Post build(){
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(this.body);
        return post;
    }
}
