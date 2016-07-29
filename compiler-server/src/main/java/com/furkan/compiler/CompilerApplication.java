package com.furkan.compiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Compiler server application that takes source code as zip file from client application
 * Compiles it using Maven, Ant or Plain Java files
 * Returns the console output to client application
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableAutoConfiguration
public class CompilerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompilerApplication.class, args);
    }
}
