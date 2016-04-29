package com.furkan.compiler.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Log client that consumes the logging service of log server
 */
public interface LogClient {

    @RequestMapping(method = RequestMethod.POST, value = "/log",
            produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    String log(String message);
}