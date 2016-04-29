package com.furkan.compiler.util;

import org.apache.ant.compress.taskdefs.Unzip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { Unzip.class, Util.class })
public class UtilTest {

    @Test
    public void findFilesInDirectoryTest() {
        // when
        List<File> files = Util.findFilesInDirectory(System.getProperty("user.dir"), "java");

        // then
        assertNotNull(files);
    }

    @Test
    public void unzipTest() throws Exception {
        // given
        boolean exceptionOccured = false;
        MultipartFile multipartFile = mock(MultipartFile.class);
        FileOutputStream fos = mock(FileOutputStream.class);
        doReturn("testDir").when(multipartFile).getOriginalFilename();
        doReturn("test".getBytes()).when(multipartFile).getBytes();

        File file = mock(File.class);
        Unzip unzip = mock(Unzip.class);
        whenNew(FileOutputStream.class).withAnyArguments().thenReturn(fos);
        whenNew(Unzip.class).withAnyArguments().thenReturn(unzip);
        whenNew(File.class).withAnyArguments().thenReturn(file);

        // when
        try {
            Util.unzip(multipartFile, "TestProject");
        } catch (IOException e) {
            exceptionOccured = true;
        }

        // then
        assertFalse(exceptionOccured);
    }
}
