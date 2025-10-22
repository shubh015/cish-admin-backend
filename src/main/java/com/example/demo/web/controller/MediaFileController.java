package com.example.demo.web.controller;

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

    // ✅ Save API
    @PostMapping("/save")
    @CrossOrigin("*")
    public String saveMedia(@RequestBody Map<String, List<Map<String, String>>> payload) {
        if (payload.containsKey("isImage")) {
            List<String> imageUrls = payload.get("isImage").stream()
                    .map(item -> item.get("images"))
                    .collect(Collectors.toList());
            service.saveMedia("image", imageUrls);
        }

        if (payload.containsKey("isVideo")) {
            List<String> videoUrls = payload.get("isVideo").stream()
                    .map(item -> item.get("video"))
                    .collect(Collectors.toList());
            service.saveMedia("video", videoUrls);
        }

        return "Media saved successfully";
    }

    // ✅ Get API (filter by type)
    @GetMapping("/get/{type}")
    @CrossOrigin("*")
    public List<MediaFile> getMedia(@PathVariable String type, @RequestParam(required = false) String role ) {
        return service.getMedia(type,role);
    }
}
