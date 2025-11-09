package com.example.demo.web.controller;

import com.example.demo.service.MinioService;
import com.example.demo.web.models.FileUpload;
import com.example.demo.repository.FileUploadRepository;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class MinioControllerTest {

    private final MinioService minioService;
    private final FileUploadRepository fileUploadRepository;

    // ✅ Upload for general use (no change)
    @PostMapping("/upload")
    @CrossOrigin("*")
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "isActive", required = false) String isActive,
            @RequestParam Map<String, String> allParams) {
        try {
            if (file != null && !file.isEmpty()) {
                Map<String, String> result = minioService.uploadFileWithMetadata(
                        file,
                        Map.of("isActive", isActive != null ? isActive : "false")
                );
                return ResponseEntity.ok(result);
            }

            String rawFile = allParams.get("file");
            if (rawFile == null || rawFile.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "No file received"));
            }

            byte[] fileBytes = rawFile.getBytes(StandardCharsets.UTF_8);
            String filename = System.currentTimeMillis() + "_upload.txt";

            try (InputStream is = new ByteArrayInputStream(fileBytes)) {
                minioService.getMinioClient().putObject(
                        PutObjectArgs.builder()
                                .bucket(minioService.getBucketName())
                                .object(filename)
                                .stream(is, fileBytes.length, -1)
                                .contentType("text/plain")
                                .userMetadata(Map.of("isActive", isActive != null ? isActive : "false"))
                                .build()
                );
            }

            String fileUrl = minioService.getMinioUrl() + "/" + minioService.getBucketName() + "/" + filename;
            return ResponseEntity.ok(Map.of("fileName", filename, "fileUrl", fileUrl));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Upload Director File & Save to DB
    @PostMapping("/upload/director")
    @CrossOrigin("*")
    public ResponseEntity<Map<String, String>> uploadDirector(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "isDirector", required = false) String isDirector,
            @RequestParam Map<String, String> allParams) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("isDirector", isDirector != null ? isDirector : "true");
            metadata.put("description", description != null ? description : "");

            String filename;
            String fileUrl;

            if (file != null && !file.isEmpty()) {
                Map<String, String> result = minioService.uploadFileWithMetadata(file, metadata);
                filename = result.get("fileName");
                fileUrl = result.get("fileUrl");
            } else {
                String rawFile = allParams.get("file");
                if (rawFile == null || rawFile.isEmpty()) {
                    return ResponseEntity.badRequest().body(Map.of("error", "No file received"));
                }

                byte[] fileBytes = rawFile.getBytes(StandardCharsets.UTF_8);
                filename = System.currentTimeMillis() + "_upload.txt";
                try (InputStream is = new ByteArrayInputStream(fileBytes)) {
                    minioService.getMinioClient().putObject(
                            PutObjectArgs.builder()
                                    .bucket(minioService.getBucketName())
                                    .object(filename)
                                    .stream(is, fileBytes.length, -1)
                                    .contentType("text/plain")
                                    .userMetadata(metadata)
                                    .build()
                    );
                }

                fileUrl = minioService.getMinioUrl() + "/" + minioService.getBucketName() + "/" + filename;
            }

            // ✅ Save metadata to DB
            FileUpload record = FileUpload.builder()
                    .fileName(filename)
                    .fileUrl(fileUrl)
                    .description(description)
                    .isActive(true)
                    .isDirector(Boolean.parseBoolean(isDirector))
                    .isImage(file != null && file.getContentType() != null && file.getContentType().startsWith("image"))
                    .isVideo(file != null && file.getContentType() != null && file.getContentType().startsWith("video"))
                    .build();

            fileUploadRepository.save(record);

            return ResponseEntity.ok(Map.of(
                    "fileName", filename,
                    "fileUrl", fileUrl,
                    "message", "✅ File uploaded and saved successfully!"
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Fetch all stored file metadata from DB
    @GetMapping("/getAll")
    @CrossOrigin("*")
    public ResponseEntity<?> getAllFilesFromDB() {
        try {
            return ResponseEntity.ok(fileUploadRepository.findByIsDirector(true));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Optional: Proxy images if needed
    @GetMapping("/proxy")
    @CrossOrigin("*")
    public ResponseEntity<byte[]> proxyFile(@RequestParam String path) throws Exception {
        URL url = new URL("http://13.234.154.152:9000/" + path);
        byte[] bytes = url.openStream().readAllBytes();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
