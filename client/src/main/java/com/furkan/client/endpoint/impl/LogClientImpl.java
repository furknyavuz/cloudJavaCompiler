package com.furkan.client.endpoint.impl;

import com.furkan.client.endpoint.LogClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Rest implementation of LogClient interface
 */
@Service("logClient")
public class LogClientImpl implements LogClient {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String log(String message) {
        // Create request
        String client = "Client: ";
        HttpEntity<String> requestEntity = new HttpEntity<>(client + message);

        // Call service
        String serviceUrl = "http://localhost:8083/log";
        ResponseEntity<String> response =  restTemplate.exchange(serviceUrl, HttpMethod.POST, requestEntity, String.class, new HashMap<>());

        return response.getBody();
    }
}
