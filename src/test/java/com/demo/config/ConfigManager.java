package com.demo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ✅ This makes your framework environment-independent (just change properties file for dev/test/prod).
 */
public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try{
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInputStream);

        } catch (IOException e){
            throw new RuntimeException("Failed to load config.properties "+ e);
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}

