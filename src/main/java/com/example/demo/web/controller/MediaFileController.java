package com.example.demo.web.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
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
    String title = null;
    Date publishDate = null;
    List<MediaFile> mediaFiles = new ArrayList<>();

    // 🧩 CASE 1: Handle standard payload (old format)
    if (payload.containsKey("title")) {
        title = (String) payload.get("title");
    }
    if (payload.containsKey("publishDate")) {
        String publishDateStr = (String) payload.get("publishDate");
        publishDate = (publishDateStr != null) ? Date.valueOf(publishDateStr) : null;
    }

    // final copies for lambdas
    final String finalTitle = title;
    final Date finalPublishDate = publishDate;

    // 🖼️ Handle Images
    if (payload.containsKey("isImage")) {
        List<Map<String, Object>> imageList = (List<Map<String, Object>>) payload.get("isImage");
        List<MediaFile> imageFiles = imageList.stream()
                .map(item -> MediaFile.builder()
                         .id(item.get("id") != null ? Long.valueOf(item.get("id").toString()) : null)
                         .backtocreator(item.get("backtocreator") != null ? Boolean.parseBoolean(item.get("backtocreator").toString()) : false) 
                        .type("image")
                        .url((String) item.get("url"))
                        .thumbnail(Boolean.TRUE.equals(item.get("thumbnail")))
                        .title(finalTitle)
                        .publishDate(finalPublishDate)
                        .createdby((String) item.get("createdby"))
                        .build())
                .collect(Collectors.toList());
        mediaFiles.addAll(imageFiles);
    }

    // 🎥 Handle Videos
    if (payload.containsKey("isVideo")) {
        List<Map<String, Object>> videoList = (List<Map<String, Object>>) payload.get("isVideo");
        List<MediaFile> videoFiles = videoList.stream()
                .map(item -> MediaFile.builder()
                         .id(item.get("id") != null ? Long.valueOf(item.get("id").toString()) : null)
                         .backtocreator(item.get("backtocreator") != null ? Boolean.parseBoolean(item.get("backtocreator").toString()) : false) 
                        .type("video")
                        .url((String) item.get("url"))
                        .thumbnail(Boolean.TRUE.equals(item.get("thumbnail")))
                        .title(finalTitle)
                        .publishDate(finalPublishDate)
                        .createdby((String) item.get("createdby"))
                        .build())
                .collect(Collectors.toList());
        mediaFiles.addAll(videoFiles);
    }

    // 🧩 Handle ABIC block
    if (payload.containsKey("abic")) {
        Map<String, Object> abic = (Map<String, Object>) payload.get("abic");

        String abicTitle = (String) abic.get("title");
        String abicName = (String) abic.get("name");
        List<String> abicImages = (List<String>) abic.get("images");

        final String finalAbicTitle = abicTitle;
        final String finalAbicName = abicName;

        if (abicImages != null && !abicImages.isEmpty()) {
            List<MediaFile> abicImageFiles = abicImages.stream()
                    .map(url -> MediaFile.builder()
                             .id(abic.get("id") != null ? Long.valueOf(abic.get("id").toString()) : null)
                            .type("abic")
                            .url(url)
                            .thumbnail(false)
                            .title(finalAbicTitle != null ? finalAbicTitle : finalAbicName)
                            .publishDate(finalPublishDate)
                             .backtocreator(abic.get("backtocreator") != null ? Boolean.parseBoolean(abic.get("backtocreator").toString()) : false)
                             .createdby((String) abic.get("createdby"))
                            .build())
                    .collect(Collectors.toList());
            mediaFiles.addAll(abicImageFiles);
        }
    }

    // 🏁 ✅ NEW: Handle Banner entries
    if (payload.containsKey("isBanner")) {
        List<Map<String, Object>> bannerList = (List<Map<String, Object>>) payload.get("isBanner");
        List<MediaFile> bannerFiles = bannerList.stream()
                .map(item -> MediaFile.builder()
                        .id(item.get("id") != null ? Long.valueOf(item.get("id").toString()) : null)
                        .type("banner")
                        .url((String) item.get("url"))
                        .bannerLink((String) item.get("bannerLink"))
                        .thumbnail(false)
                        .isBannerFirst((Boolean) item.get("isBannerFirst"))
                        .title(finalTitle)
                        .publishDate(finalPublishDate)
                        .backtocreator(item.get("backtocreator") != null ? Boolean.parseBoolean(item.get("backtocreator").toString()) : false)
                        .createdby((String) item.get("createdby"))
                        .build())
                .collect(Collectors.toList());
        mediaFiles.addAll(bannerFiles);
    }

     if (payload.containsKey("isDirector")) {
        List<Map<String, Object>> bannerList = (List<Map<String, Object>>) payload.get("isDirector");
        List<MediaFile> bannerFiles = bannerList.stream()
                .map(item -> MediaFile.builder()
                        .id(item.get("id") != null ? Long.valueOf(item.get("id").toString()) : null)
                        .type("director")
                        .url((String) item.get("url"))
                        .description(item.get("description") != null ? item.get("description").toString() : null)
                        .backtocreator(item.get("backtocreator") != null ? Boolean.parseBoolean(item.get("backtocreator").toString()) : false)
                        .createdby((String) item.get("createdby"))
                        .build())
                .collect(Collectors.toList());
        mediaFiles.addAll(bannerFiles);
    }

    // ✅ Save all collected media entries
    if (!mediaFiles.isEmpty()) {
        service.saveAll(mediaFiles);
    }

    return "✅ Media saved successfully";
}


    @GetMapping("/get/{type}")
    @CrossOrigin("*")
    public List<MediaFile> getMedia(@PathVariable String type, @RequestParam(required = false) String role) {
        return service.getMedia(type, role);
    }



        @PostMapping("/status")
    @CrossOrigin("*")
    public ResponseEntity<String> updateKeyContentStatus(@RequestBody Map<String, Object> payload) {
        try {
            String key = (String) payload.get("key");
            if (key == null || !payload.containsKey("ids")) {
                return ResponseEntity.badRequest().body("❌ Missing 'key' or 'ids' in request.");
            }

            // ✅ Safely convert ids to List<Long>
            List<Long> ids = ((List<?>) payload.get("ids"))
                    .stream()
                    .map(id -> Long.valueOf(String.valueOf(id)))
                    .toList();

            if (ids.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ 'ids' list cannot be empty.");
            }

            switch (key.toLowerCase()) {
                case "publish" -> service.updateStatus(ids, true, true, false);
                case "delete" -> service.updateStatus(ids, null, false, null);
                case "backtocreator" -> service.updateStatus(ids, false, null, true);
                default -> {
                    return ResponseEntity.badRequest()
                            .body("❌ Invalid key. Use 'publish', 'delete', or 'backToCreator'.");
                }
            }

            return ResponseEntity.ok("✅ Status updated for " + ids.size() + " record(s) with action: " + key);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error: " + e.getMessage());
        }
    }
}
