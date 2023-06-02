package com.myapp.blog.service.impl;

import com.myapp.blog.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //file name
        String name = file.getOriginalFilename();

        //generate id of particular file
        String randomID = UUID.randomUUID().toString();
        String fileNameId = randomID.concat(name.substring(name.lastIndexOf(".")));

        //full path to save
        String filePath = path + File.separator + fileNameId;


        //create folder if not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        //files copy (where to copy)
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileNameId;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);

        //db logic
        return is ;
    }
}
