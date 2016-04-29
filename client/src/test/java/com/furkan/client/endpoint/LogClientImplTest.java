package com.furkan.client.endpoint;

import com.furkan.client.endpoint.impl.LogClientImpl;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.reflect.Whitebox.setInternalState;

public class LogClientImplTest {

    @Test
    public void testLog() {
        // given
        RestTemplate restTemplate = mock(RestTemplate.class);
        LogClientImpl logClientImpl = new LogClientImpl();
        ResponseEntity response = mock(ResponseEntity.class);
        doReturn("Success").when(response).getBody();
        doReturn(response).when(restTemplate).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class), any(HashMap.class));
        setInternalState(logClientImpl, "restTemplate", restTemplate);

        // when
        String result = logClientImpl.log("Message");

        // then
        assertEquals("Success", result);
    }
}
