package com.menufoods.services.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;


@Service
public class UploadService implements IUploadService {

    String uploadDirectoryPath = "src/main/java/com/menufoods/uploads";
    @Value("${api.path.url}")
    String pathByDocker;


    @Override
    public String uploadFile(MultipartFile file, String filename) {


        try {
            File directory = new File(this.uploadDirectoryPath);

            if(!directory.exists()) {
                directory.mkdirs();
            }

            File serverFile = new File(directory.getAbsolutePath() + File.separator + filename);
            if(serverFile.exists()) {
                serverFile.delete();
            }

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();

            System.out.println(pathByDocker);

            if(Objects.equals(pathByDocker, "NotFromDocker")) {
                return serverFile.getPath();
            }

            return pathByDocker;

        } catch(Exception e) {
            throw new RuntimeException("Não foi possível realizar o upload");
        }
    }

    @Override
    public void deleteFile(String url) {
        int lastIndex = url.lastIndexOf("\\");
        String filename = url.substring(lastIndex + 1);

        System.out.println("Nome do arquivo: " + filename);
        File directory = new File(this.uploadDirectoryPath);
        File serverFile = new File(directory.getAbsolutePath() + File.separator + filename);
        serverFile.delete();
    }
}
