package com.example.demo.web.controller;



import com.example.demo.service.MinioService;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class MinioControllerTest {

    private final MinioService minioService;

    // Generic upload (works with both Postman & your frontend)
    @PostMapping("/upload")
    @CrossOrigin("*")
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "isActive", required = false) String isActive,
            @RequestParam Map<String, String> allParams) {
        try {
            // ✅ Case 1: Proper MultipartFile (Postman or correct frontend)
            if (file != null && !file.isEmpty()) {
                Map<String, String> result = minioService.uploadFileWithMetadata(
                        file,
                        Map.of("isActive", isActive != null ? isActive : "false")
                );
                return ResponseEntity.ok(result);
            }

            // ✅ Case 2: Frontend sends text or base64 instead of a real file
            String rawFile = allParams.get("file");
            if (rawFile == null || rawFile.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "No file received"));
            }

            byte[] fileBytes;
            String filename;
            String contentType = "text/plain";

            if (rawFile.startsWith("data:")) {
                // Example: data:image/png;base64,iVBORw0KGgo...
                String base64Data = rawFile.substring(rawFile.indexOf(",") + 1);
                fileBytes = Base64.getDecoder().decode(base64Data);
                filename = System.currentTimeMillis() + "_upload.png";
                contentType = "image/png";
            } else {
                // Example: "[object Object]" → save as text
                fileBytes = rawFile.getBytes(StandardCharsets.UTF_8);
                filename = System.currentTimeMillis() + "_upload.txt";
            }

            try (InputStream is = new ByteArrayInputStream(fileBytes)) {
                minioService.getMinioClient().putObject(
                        PutObjectArgs.builder()
                                .bucket(minioService.getBucketName())
                                .object(filename)
                                .stream(is, fileBytes.length, -1)
                                .contentType(contentType)
                                .userMetadata(Map.of("isActive", isActive != null ? isActive : "false"))
                                .build()
                );
            }

            String fileUrl = minioService.getMinioUrl() + "/" + minioService.getBucketName() + "/" + filename;
            return ResponseEntity.ok(Map.of("fileName", filename, "fileUrl", fileUrl));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // Director desk upload
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

            // ✅ Works for both file & text input
            if (file != null && !file.isEmpty()) {
                Map<String, String> result = minioService.uploadFileWithMetadata(file, metadata);
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
                                .userMetadata(metadata)
                                .build()
                );
            }

            String fileUrl = minioService.getMinioUrl() + "/" + minioService.getBucketName() + "/" + filename;
            return ResponseEntity.ok(Map.of("fileName", filename, "fileUrl", fileUrl));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // Get all files
    @GetMapping("/getAll")
    @CrossOrigin("*")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(minioService.getAllFiles());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
