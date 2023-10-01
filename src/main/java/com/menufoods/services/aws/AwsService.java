package com.menufoods.services.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import static com.menufoods.infra.utils.Utils.convertMultiPartToFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AwsService {

    @Autowired
    AmazonS3 amazonS3;
    @Value("${cloud.aws.bucket.name}")
    String bucketName;
    @Value("${application.upload.files.s3-url-format}")
    String endpoint;

    public String uploadInS3Bucket(MultipartFile file, String filename) throws Exception {
        var fileConverted = convertMultiPartToFile(file);
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, fileConverted).withCannedAcl(CannedAccessControlList.PublicRead));
        var fileUri = endpoint + "/" + filename;
        fileConverted.delete();
        return fileUri;
    }

    public void deleteItemFromS3Bucket(String key) {
        amazonS3.deleteObject(bucketName, key);
    }


}
