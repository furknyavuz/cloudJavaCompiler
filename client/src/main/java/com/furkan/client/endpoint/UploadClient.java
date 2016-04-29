package com.furkan.client.endpoint;

import org.springframework.web.multipart.MultipartFile;

/**
 * Upload client that consumes the upload service of compiler server
 */
public interface UploadClient {

    byte[] upload(MultipartFile file);
}