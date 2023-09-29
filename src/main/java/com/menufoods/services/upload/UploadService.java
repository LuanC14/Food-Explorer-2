package com.menufoods.services.upload;

import com.menufoods.services.aws.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadService implements iUploadService {

    @Autowired
    AwsService awsService;
    @Override
    public String uploadFile(MultipartFile file, String filename) {
        try {
            return awsService.uploadInS3Bucket(file, filename);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void deleteFile(String key) {
        awsService.deleteItemFromS3Bucket(key);
    }
}
