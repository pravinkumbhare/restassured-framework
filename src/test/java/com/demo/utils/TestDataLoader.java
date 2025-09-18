package com.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestDataLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Generic loader: returns any class type you want
    public static <T> T loadData(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File("src/test/resources/testdata/" + filePath), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data from file: " + filePath, e);
        }
    }
}
