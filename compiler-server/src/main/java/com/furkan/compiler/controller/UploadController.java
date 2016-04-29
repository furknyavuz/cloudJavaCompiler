package com.furkan.compiler.controller;


import com.furkan.compiler.endpoint.LogClient;
import com.furkan.compiler.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Controller for get the source code from client application
 * and returns the console output
 */
@RestController
public class UploadController {

    @Autowired
    @Qualifier("logClient")
    private LogClient logClient;

    @Autowired
    @Qualifier("compileController")
    private CompileController compileController;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public byte[] uploadFile(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {

        byte[] bytes = null;

        try {
            // Send file captured message to log server
            logClient.log("File successfully captured at compiler server: " + file.getOriginalFilename());

            // Generate project name
            Date date = new Date();
            String projectName = "project" + date.getTime();

            // Unzip
            Util.unzip(file, projectName);

            // Send zip file is valid message to server
            logClient.log("File is valid zip file: " + file.getOriginalFilename());

            // Compile the source code
            String outputFile = compileController.compile(projectName, file.getOriginalFilename());

            // Prepare response

            // No output case
            if(outputFile == null) {
                return "Compile output is empty".getBytes();
            }

            // Read console output from file
            File consoleOutputFile = new File(outputFile);
            if (consoleOutputFile != null && consoleOutputFile.exists()) {

                InputStream inputStream = new FileInputStream(consoleOutputFile);
                long length = consoleOutputFile.length();

                // Output is empty
                // This can be true for plain java files without errors
                if(length <= 0) {
                    return "Compile output is empty".getBytes();
                }

                // Read output from file
                bytes = new byte[(int)length];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length
                        && (numRead = inputStream.read(bytes, offset, bytes.length-offset)) >= 0) {
                    offset += numRead;
                }

                // Could not completely read
                if (offset < bytes.length) {
                    return"Could not completely read file ".getBytes();
                }
                inputStream.close();
            }
        } catch (Exception e) {
            logClient.log("Project compilation end at compiler server: " + e.getMessage());
        }
        logClient.log("Project compilation end at compiler server: " + file.getOriginalFilename());
        return bytes;
    }

}
