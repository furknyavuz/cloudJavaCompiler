package com.furkan.log.contoller;

import com.furkan.log.controller.LogController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogControllerTest {

    @Test
    public void testLog() {
        // given
        LogController logController = new LogController();

        // when
        String result = logController.logMessage("Message");

        // then
        assertEquals("Logged", result);
    }
}
