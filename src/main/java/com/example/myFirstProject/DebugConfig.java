package com.example.myFirstProject;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
    public class DebugConfig {

        @Autowired
        private Environment env;

        @PostConstruct
        public void printMongoConfig() {
            System.out.println("DB: " + env.getProperty("spring.data.mongodb.database"));
            System.out.println("URI: " + env.getProperty("spring.data.mongodb.uri"));
        }
    }