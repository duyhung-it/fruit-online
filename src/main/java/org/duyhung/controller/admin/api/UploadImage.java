package org.duyhung.controller.admin.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class UploadImage {
    @PostMapping("/api/upload")
    public String create(@RequestBody MultipartFile file) {
        String currentDirectory = System.getProperty("user.dir");
        String absoluteFilePath = currentDirectory + "/src/main/resources/static/image/";
        String fileName = file.getOriginalFilename();
        String filePath = absoluteFilePath + fileName;
        try {
            file.transferTo(new File(filePath));
            return fileName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
