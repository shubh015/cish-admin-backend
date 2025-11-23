package com.example.demo.web.controller;


import com.example.demo.service.NewsEventService;
import com.example.demo.web.models.event.NewsEvent;
import com.example.demo.web.models.event.NewsEventResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@CrossOrigin("*")
public class NewsEventController {

    private final NewsEventService service;

    public NewsEventController(NewsEventService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @CrossOrigin("*")
    public Object saveNews(@RequestBody Map<String, Object> request) {

        if (request.containsKey("newsEvent")) {
            Map<String, Object> newsMap = (Map<String, Object>) request.get("newsEvent");
            NewsEvent event = mapToEntity(newsMap, "newsEvent");
            return service.save(event);

        } else if (request.containsKey("vksa")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) request.get("vksa");

            List<NewsEvent> events = list.stream()
                    .map(m -> mapToEntity(m, "vksa"))
                    .collect(Collectors.toList());

            return service.saveAll(events);

        } else if (request.containsKey("abic")) {
            Map<String, Object> abicMap = (Map<String, Object>) request.get("abic");
            NewsEvent event = mapToEntity(abicMap, "abic");
            return service.save(event);

        } else {
            throw new IllegalArgumentException("Invalid payload: expected 'newsEvent', 'vksa', or 'abic'");
        }
    }

    // ✅ GET API by type, returns clean JSON
    @GetMapping
    @CrossOrigin("*")
    public List<NewsEventResponse> getByType(@RequestParam String type, @RequestParam(required = false) String role) {
        if (!type.equals("newsEvent") && !type.equals("vksa") && !type.equals("abic")) {
            throw new IllegalArgumentException("Type must be 'newsEvent', 'vksa', or 'abic'");
        }
        List<NewsEvent> events = service.getByType(type, role);
        return events.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
@SuppressWarnings("unchecked")
private NewsEvent mapToEntity(Map<String, Object> map, String type) {

    NewsEvent e;

    // ✅ 1. Check if ID exists → update, else insert
    Object idObj = map.get("id");
    if (idObj != null) {
        Long id = Long.valueOf(String.valueOf(idObj));

        // Try to load existing entity
        e = service.findById(id);
        if (e == null) {
            throw new RuntimeException("NewsEvent not found with ID: " + id);
        }

        e.setBacktocreator(Boolean.parseBoolean(map.get("backtocreator").toString()));

        // Clear old images (orphanRemoval = true will delete from DB)
        e.getImages().clear();
        e.setId(id);

    } else {
        // Insert new
        e = new NewsEvent();
    }

    // Set basic fields
    e.setName((String) map.get("name"));
    e.setTitle((String) map.get("title"));
    e.setType(type);

    // Dates
    if (map.get("date") != null)
        e.setDate(java.sql.Date.valueOf((String) map.get("date")));
    if (map.get("startDate") != null)
        e.setStartDate(java.sql.Date.valueOf((String) map.get("startDate")));
    if (map.get("endDate") != null)
        e.setEndDate(java.sql.Date.valueOf((String) map.get("endDate")));

    // Images
    Object imagesObj = map.get("images");
    if (imagesObj instanceof List<?>) {
        List<?> imagesList = (List<?>) imagesObj;

        for (Object img : imagesList) {
            if (img instanceof Map) {
                Map<String, Object> imgMap = (Map<String, Object>) img;
                String url = (String) imgMap.get("url");
                boolean thumbnail = Boolean.TRUE.equals(imgMap.get("thumbnail"));
                boolean banner = Boolean.TRUE.equals(imgMap.get("banner"));
                e.addImage(url, thumbnail, banner);
            } else if (img instanceof String) {
                e.addImage((String) img, false, false);
            }
        }
    }

    return e;
}

    // ✅ Entity → Response mapper
    private NewsEventResponse mapToResponse(NewsEvent e) {
        NewsEventResponse res = new NewsEventResponse();
        res.setId(e.getId());
        res.setName(e.getName());
        res.setTitle(e.getTitle());
        res.setDate(e.getDate());
        res.setStartDate(e.getStartDate());
        res.setEndDate(e.getEndDate());
        res.setType(e.getType());

        // ✅ Map both URL and thumbnail flag into ImageResponse objects
        List<NewsEventResponse.ImageResponse> imageResponses = e.getImages().stream()
                .map(img -> {
                    NewsEventResponse.ImageResponse ir = new NewsEventResponse.ImageResponse();
                    ir.setUrl(img.getImageUrl());
                    ir.setThumbnail(img.getThumbnail());
                    return ir;
                })
                .collect(Collectors.toList());

        res.setImages(imageResponses);
        return res;
    }



// No change needed for the /status endpoint
@PostMapping("/status")
@CrossOrigin("*")
public ResponseEntity<String> updateNewsEventStatus(@RequestBody Map<String, Object> payload) {
    try {
        String key = (String) payload.get("key");
        if (key == null || !payload.containsKey("ids")) {
            return ResponseEntity.badRequest().body("❌ Missing 'key' or 'ids' in request.");
        }

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
