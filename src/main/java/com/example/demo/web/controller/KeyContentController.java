package com.example.demo.web.controller;

import com.example.demo.service.KeyContentService;
import com.example.demo.web.models.content.KeyContent;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/content")
@CrossOrigin("*")
public class KeyContentController {

    private final KeyContentService service;

    public KeyContentController(KeyContentService service) {
        this.service = service;
    }

    /**
     * ✅ POST /api/content/save
     * Each request contains only one key (like keyResearch, jobs, tenders, etc.)
     */
    @PostMapping("/save")
     @CrossOrigin("*")
    public String saveContent(@RequestBody Map<String, List<Map<String, Object>>> payload) {
        if (payload.isEmpty()) {
            return "❌ No content provided!";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        payload.forEach((key, value) -> {
            List<KeyContent> contents = value.stream().map(item -> KeyContent.builder()
                    .title((String) item.get("title"))
                    .description((String) item.get("description"))
                    .imageUrl((String) item.get("images"))
                    .date(item.get("date") != null ? LocalDate.parse((String) item.get("date"), formatter) : null)
                    .postDate(item.get("postDate") != null ? LocalDate.parse((String) item.get("postDate"), formatter)
                            : null)
                    .lastDate(item.get("lastDate") != null ? LocalDate.parse((String) item.get("lastDate"), formatter)
                            : null)
                    .build())
                    .toList();

            service.saveContents(key, contents);
        });

        return "✅ Content saved successfully!";
    }

    /**
     * ✅ GET /api/content/{key}
     */
    @GetMapping("/{key}")
     @CrossOrigin("*")
    public List<KeyContent> getContentByKey(@PathVariable String key,@RequestParam(required = false) String role) {
        return service.getContents(key,role);
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

