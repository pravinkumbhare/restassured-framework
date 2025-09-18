package com.demo.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class JUnit5ParameterizedTests {

//  Example: Inline values
    @ParameterizedTest
    @Tag("ParameterizedTest")
    @ValueSource(strings = {"API Test", "Rest Assured", "JUnit5"})
    public void testWithStrings(String title){
        System.out.println("Running test with title: " + title);
    }

//    Example: CSV Source
    @ParameterizedTest
    @Tag("ParameterizedTest")
    @CsvSource({
            "101, First title",
            "202, Another title",
            "303, Last title"
    })
    public void testPosts(int userId, String title){
        System.out.println("UserId: " + userId + ", Title: " + title);
    }
}
