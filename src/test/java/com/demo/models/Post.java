package com.demo.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Post {
    private int userId;
    private String title;
    private String body;
    private int id;
}
