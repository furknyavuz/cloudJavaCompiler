package com.furkan.client.controller;

import com.furkan.client.endpoint.LogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for log to server
 */
@RestController
public class LogController {

    @Autowired
    @Qualifier("logClient")
    private LogClient logClient;

    @RequestMapping("/sendlog")
    public String sendMessage(@RequestBody String message) {
        return logClient.log(message);
    }
}
