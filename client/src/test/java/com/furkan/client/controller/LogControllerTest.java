package com.furkan.client.controller;

import com.furkan.client.endpoint.LogClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.reflect.Whitebox.setInternalState;

public class LogControllerTest {

    @Test
    public void testLog() {
        // given
        LogController logController = new LogController();
        LogClient logClient = mock(LogClient.class);
        doReturn("Success").when(logClient).log("Message");
        setInternalState(logController, "logClient", logClient);

        // when
        String result = logController.sendMessage("Message");

        // then
        assertEquals("Success", result);
    }

}
