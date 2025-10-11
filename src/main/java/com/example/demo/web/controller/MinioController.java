package com.example.demo.web.controller;




import com.example.demo.service.MinioService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class MinioController {
    
    @Autowired
    private  MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(minioService.uploadFile(file));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

 @GetMapping("/{fileName}")
public ResponseEntity<Map<String, String>> getFile(@PathVariable String fileName) {
    try {
        return ResponseEntity.ok(minioService.getFileInfo(fileName));
    } catch (Exception e) {
        return ResponseEntity.internalServerError()
                             .body(Map.of("error", e.getMessage()));
    }
}

}
