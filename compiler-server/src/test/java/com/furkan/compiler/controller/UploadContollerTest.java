package com.furkan.compiler.controller;

import com.furkan.compiler.endpoint.LogClient;
import com.furkan.compiler.util.Util;
import org.apache.ant.compress.taskdefs.Unzip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { Unzip.class, UploadController.class, Util.class })
public class UploadContollerTest {

    @Test
    public void testUploadFile() throws Exception {
        // given
        UploadController uploadController = new UploadController();

        CompileController compileController = mock(CompileController.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        FileOutputStream fos = mock(FileOutputStream.class);
        FileInputStream fis = mock(FileInputStream.class);
        LogClient logClient = mock(LogClient.class);
        Unzip unzip = mock(Unzip.class);
        File file = mock(File.class);

        doReturn("Success").when(logClient).log("Message");
        doReturn("testDir").when(multipartFile).getOriginalFilename();
        doReturn("test".getBytes()).when(multipartFile).getBytes();
        doReturn("tempFile").when(multipartFile).getOriginalFilename();
        doReturn("tempFile").when(compileController).compile(anyString(), anyString());
        doReturn(true).when(file).exists();
        doReturn(1L).when(file).length();
        doReturn(1).when(fis).read(any(byte[].class), anyInt(), anyInt());

        setInternalState(uploadController, "logClient", logClient);
        setInternalState(uploadController, "compileController", compileController);

        whenNew(FileOutputStream.class).withAnyArguments().thenReturn(fos);
        whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
        whenNew(Unzip.class).withAnyArguments().thenReturn(unzip);
        whenNew(File.class).withAnyArguments().thenReturn(file);

        // when
        byte[] result = uploadController.uploadFile(multipartFile);

        // then
        assertEquals(result.length, 1);
    }
}
