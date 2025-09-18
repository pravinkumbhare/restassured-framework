package com.demo.tests.restAssuredTopic.BuilderPatternConcept;

import com.demo.models.Post;
import com.demo.utils.ApiUtils;
import org.junit.jupiter.api.Test;

public class PostBuilder {

    private int userId = 100;
    private String title = "Default Builder Title";
    private String body = "Default Builder Body";

    public PostBuilder withUserId(int userId){
        this.userId = userId;
        return this;
    }

    public PostBuilder withTitle(String title){
        this.title = title;
        return this;
    }

    public PostBuilder withBody(String body){
        this.body = body;
        return this;
    }

    public Post build(){
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);
        return post;
    }


}
