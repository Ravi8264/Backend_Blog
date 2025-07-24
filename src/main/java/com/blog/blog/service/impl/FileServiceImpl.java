package com.blog.blog.service.impl;

import com.blog.blog.service.FileService;
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
        // Get original filename
        String originalFilename = file.getOriginalFilename();

        // Generate unique filename to avoid conflicts
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        // Create full path
        String fullPath = path + File.separator + uniqueFilename;

        // Create directory if it doesn't exist
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Copy file to the specified path
        Files.copy(file.getInputStream(), Paths.get(fullPath));

        return uniqueFilename;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullPath = path + File.separator + filename;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
