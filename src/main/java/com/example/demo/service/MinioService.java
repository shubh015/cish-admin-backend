package com.example.demo.service;

import io.minio.*;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class MinioService {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.secret.key}")
    private String secretKey;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.secure:false}")
    private boolean secure;

    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();

        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing MinIO: " + e.getMessage(), e);
        }
    }

    private String sanitizeFileName(String original) {
        return original.replaceAll("\\s+", "_");
    }

    public Map<String, String> uploadFile(MultipartFile file) throws Exception {
        String filename = System.currentTimeMillis() + "_" + sanitizeFileName(file.getOriginalFilename());
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
        String fileUrl = minioUrl + "/" + bucketName + "/" + filename;
        return Map.of("fileName", filename, "fileUrl", fileUrl);
    }

    public Map<String, String> uploadFileWithMetadata(MultipartFile file, Map<String, String> metadata) throws Exception {
        String filename = System.currentTimeMillis() + "_" + sanitizeFileName(file.getOriginalFilename());
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .userMetadata(metadata)
                            .build()
            );
        }
        String fileUrl = minioUrl + "/" + bucketName + "/" + filename;
        return Map.of("fileName", filename, "fileUrl", fileUrl);
    }

    public List<Map<String, String>> getAllFiles() throws Exception {
        Iterable<Result<Item>> items = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        List<Map<String, String>> files = new ArrayList<>();
        for (Result<Item> itemResult : items) {
            Item item = itemResult.get();
            String url = minioUrl + "/" + bucketName + "/" + item.objectName();
            files.add(Map.of("fileName", item.objectName(), "fileUrl", url));
        }
        return files;
    }
}
