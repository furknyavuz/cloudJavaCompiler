package com.furkan.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Client application that takes source code as zip file from user
 * Pass it to compiler server
 * Gets the console output from compiler server
 * Display at browser
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
