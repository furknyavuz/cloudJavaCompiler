package com.furkan.compiler.endpoint.impl;

import com.furkan.compiler.endpoint.LogClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String log(String message) {
        // Create request
        String client = "Compiler: ";
        HttpEntity<String> requestEntity = new HttpEntity<>(client + message);

        // Call service
        String serviceUrl = "http://log-server/log";
        ResponseEntity<String> response =  restTemplate.exchange(serviceUrl, HttpMethod.POST, requestEntity, String.class, new HashMap<>());

        return response.getBody();
    }
}
