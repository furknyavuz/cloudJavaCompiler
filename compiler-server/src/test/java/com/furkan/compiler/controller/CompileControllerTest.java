package com.furkan.compiler.controller;

import com.furkan.compiler.endpoint.LogClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CompileController.class)
public class CompileControllerTest {

    @Test
    public void testCompile() throws Exception {
        // given
        File file = mock(File.class);
        doReturn(true).when(file).exists();
        whenNew(File.class).withAnyArguments().thenReturn(file);
        CompileController compileController = new CompileController();
        LogClient logClient = mock(LogClient.class);
        doReturn("Success").when(logClient).log("Message");
        setInternalState(compileController, "logClient", logClient);
        String expected  = "testFile.out";

        // when
        String result = compileController.compile("testProject", "testFile");

        // then
        assertEquals(expected, result);
    }
}
