package com.furkan.client.endpoint;

import com.furkan.client.endpoint.impl.UploadClientImpl;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.reflect.Whitebox.setInternalState;

public class UploadClientImplTest {

    @Test
    public void testUpload() throws IOException {
        // given
        MultipartFile multipartFile = mock(MultipartFile.class);
        doReturn("Test").when(multipartFile).getOriginalFilename();
        doReturn("Success".getBytes()).when(multipartFile).getBytes();
        RestTemplate restTemplate = mock(RestTemplate.class);
        UploadClientImpl uploadClient = new UploadClientImpl();
        ResponseEntity response = mock(ResponseEntity.class);
        byte[] expected = "Success".getBytes();
        doReturn(expected).when(response).getBody();
        setInternalState(uploadClient, "restTemplate", restTemplate);
        doReturn(response).when(restTemplate).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));

        // when
        byte[] result = uploadClient.upload(multipartFile);

        // then
        assertEquals(expected, result);
    }
}
