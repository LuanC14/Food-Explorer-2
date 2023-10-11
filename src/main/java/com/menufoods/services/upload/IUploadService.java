package com.menufoods.services.upload;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    String uploadFile(MultipartFile file, String filename);

    void deleteFile(String key);
}
