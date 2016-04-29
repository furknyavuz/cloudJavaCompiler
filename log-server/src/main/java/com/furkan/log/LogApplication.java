package com.furkan.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Log server application that takes log messages from clients
 * Saves them into log files and prints to system out
 * Using log4j
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }
}
