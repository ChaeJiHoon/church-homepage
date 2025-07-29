package com.church.church_homepage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Value("${test.message}")
    private String testMessage;

    @Bean
    public CommandLineRunner testMessageRunner(){
        return args -> {
            System.out.println("-----------------------------------------------------");
            System.out.println("Test message from application.yml: " + testMessage);
            System.out.println("-----------------------------------------------------");
        };
    }


}
