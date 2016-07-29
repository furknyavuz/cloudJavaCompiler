package com.furkan.client.endpoint.impl;

import com.furkan.client.endpoint.LogClient;
import com.furkan.client.endpoint.UploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Rest implementation of UploadClient interface
 */
@Service("uploadClient")
public class UploadClientImpl implements UploadClient {

    @Autowired
    @Qualifier("logClient")
    private LogClient logClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public byte[] upload(MultipartFile file) {
        File convertedFile = null;

        try {
            // Create request
            final MultiValueMap<String, Object> requestParts = new LinkedMultiValueMap<>();

            // Convert MultipartFile to java file
            final String fileName = file.getOriginalFilename();
            convertedFile = new File(file.getOriginalFilename());
            boolean isFileCreated = convertedFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(file.getBytes());
            fos.close();

            // Add file to request
            requestParts.add("file", new FileSystemResource(fileName));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "multipart/form-data");

            // Call service
            String serviceUrl = "http://compiler-server/upload";
            ResponseEntity<byte[]> response = restTemplate.exchange(serviceUrl, HttpMethod.POST, new HttpEntity<>(requestParts, headers), byte[].class);
            return response.getBody();

        } catch (Exception ex) {

            // Log all exceptions to server
            logClient.log("Exception occured during sending file to compiler server: " + ex.getMessage());
        } finally {

            // Delete uploaded file
            if (convertedFile != null && convertedFile.exists()) {
                boolean isFileDeleted = convertedFile.delete();
            }
        }

        return null;
    }
}
