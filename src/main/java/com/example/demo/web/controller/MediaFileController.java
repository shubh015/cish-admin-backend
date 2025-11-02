package com.example.demo.web.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MediaFileService;
import com.example.demo.web.models.media.MediaFile;

import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin("*")
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaFileController {

    private final MediaFileService service;

    @PostMapping("/save")
    @CrossOrigin("*")
    public String saveMedia(@RequestBody Map<String, Object> payload) {

        String title = (String) payload.get("title");
        String publishDateStr = (String) payload.get("publishDate");
        Date publishDate = (publishDateStr != null) ? Date.valueOf(publishDateStr) : null;

        // 🧩 Handle image uploads
        if (payload.containsKey("isImage")) {
            List<Map<String, Object>> imageList = (List<Map<String, Object>>) payload.get("isImage");
            List<MediaFile> imageFiles = imageList.stream()
                    .map(item -> MediaFile.builder()
                            .type("image")
                            .url((String) item.get("url"))
                            .thumbnail(Boolean.TRUE.equals(item.get("thumbnail")))
                            .title(title)
                            .publishDate(publishDate)
                            .build())
                    .collect(Collectors.toList());
            service.saveAll(imageFiles);
        }

        // 🎥 Handle video uploads
        if (payload.containsKey("isVideo")) {
            List<Map<String, Object>> videoList = (List<Map<String, Object>>) payload.get("isVideo");
            List<MediaFile> videoFiles = videoList.stream()
                    .map(item -> MediaFile.builder()
                            .type("video")
                            .url((String) item.get("url"))
                            .thumbnail(Boolean.TRUE.equals(item.get("thumbnail")))
                            .title(title)
                            .publishDate(publishDate)
                            .build())
                    .collect(Collectors.toList());
            service.saveAll(videoFiles);
        }

        return "✅ Media saved successfully";
    }

    @GetMapping("/get/{type}")
    @CrossOrigin("*")
    public List<MediaFile> getMedia(@PathVariable String type, @RequestParam(required = false) String role) {
        return service.getMedia(type, role);
    }
}
