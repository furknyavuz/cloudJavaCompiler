package com.furkan.client.controller;

import com.amazonaws.util.json.JSONException;
import com.furkan.client.endpoint.UploadClient;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.reflect.Whitebox.setInternalState;

public class UploadControllerTest {

    @Test
    public void testUpload() throws IOException, JSONException {
        // given
        MultipartFile multipartFile = mock(MultipartFile.class);
        UploadController uploadController = new UploadController();
        UploadClient uploadClient = mock(UploadClient.class);
        doReturn("Success".getBytes()).when(uploadClient).upload(multipartFile);
        setInternalState(uploadController, "uploadClient", uploadClient);

        // when
        String result = uploadController.uploadFile(multipartFile);

        // then
        assertEquals("{\"result\":[\"Success\"]}", result);
    }

}
