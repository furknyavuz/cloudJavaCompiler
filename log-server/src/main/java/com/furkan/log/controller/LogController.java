package com.furkan.log.controller;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling log message requests
 */
@RestController
public class LogController {

    private final static Logger logger = Logger.getLogger(LogController.class);

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public String logMessage(@RequestBody String input) {
        // Log messages via log4j logger
        logger.log(Level.INFO, input);
        return "Logged";
    }
}
