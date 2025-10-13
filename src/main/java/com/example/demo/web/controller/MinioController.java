package com.example.demo.web.controller;

import com.example.demo.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class MinioController {

    private final MinioService minioService;

    // Generic upload (banner etc.)
    @PostMapping("/upload")
     @CrossOrigin("*")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "isActive", required = false) String isActive) {
        try {
            Map<String, String> result = minioService.uploadFileWithMetadata(file,
                    Map.of("isActive", isActive != null ? isActive : "false"));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // Director desk upload
    @PostMapping("/upload/director")
     @CrossOrigin("*")
    public ResponseEntity<Map<String, String>> uploadDirector(@RequestParam("file") MultipartFile file,
                                                              @RequestParam(value = "description", required = false) String description,
                                                              @RequestParam(value = "isDirector", required = false) String isDirector) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("isDirector", isDirector != null ? isDirector : "true");
            metadata.put("description", description != null ? description : "");
            Map<String, String> result = minioService.uploadFileWithMetadata(file, metadata);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // Get all files
    @GetMapping("/getAll")
     @CrossOrigin("*")
    public ResponseEntity<List<Map<String, String>>> getAll() {
        try {
            return ResponseEntity.ok(minioService.getAllFiles());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
