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

    // Save API for newsEvent or vksa
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
        } else {
            throw new IllegalArgumentException("Invalid payload: expected 'newsEvent' or 'vksa'");
        }
    }

    // GET API by type, returns clean JSON
    @GetMapping
     @CrossOrigin("*")
    public List<NewsEventResponse> getByType(@RequestParam String type, @RequestParam(required = false) String role) {
        if (!type.equals("newsEvent") && !type.equals("vksa")) {
            throw new IllegalArgumentException("Type must be 'newsEvent' or 'vksa'");
        }
        List<NewsEvent> events = service.getByType(type,role);
        return events.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Payload → Entity mapper
    private NewsEvent mapToEntity(Map<String, Object> map, String type) {
        NewsEvent e = new NewsEvent();
        e.setName((String) map.get("name"));
        e.setTitle((String) map.get("title"));
        e.setDate(Date.valueOf((String) map.get("date")));
        e.setType(type);

        List<String> images = (List<String>) map.get("images");
        if (images != null) {
            images.forEach(e::addImage);
        }

        return e;
    }

    // Entity → DTO mapper
    private NewsEventResponse mapToResponse(NewsEvent e) {
        NewsEventResponse res = new NewsEventResponse();
        res.setId(e.getId());
        res.setName(e.getName());
        res.setTitle(e.getTitle());
        res.setDate(e.getDate());
        res.setType(e.getType());
        res.setImages(e.getImages().stream()
                .map(img -> img.getImageUrl())
                .collect(Collectors.toList()));
        return res;
    }



    @PostMapping("/status")
    @CrossOrigin("*")
    public ResponseEntity<String> updateNewsEventStatus(@RequestBody Map<String, Object> payload) {
        try {
            String key = (String) payload.get("key");
            if (key == null || !payload.containsKey("ids")) {
                return ResponseEntity.badRequest().body("❌ Missing 'key' or 'ids' in request.");
            }

            // Convert ids safely
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
