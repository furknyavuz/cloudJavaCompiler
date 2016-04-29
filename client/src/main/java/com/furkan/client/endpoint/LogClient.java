package com.furkan.client.endpoint;

/**
 * Log client that consumes the logging service of log server
 */
public interface LogClient {

    String log(String message);
}