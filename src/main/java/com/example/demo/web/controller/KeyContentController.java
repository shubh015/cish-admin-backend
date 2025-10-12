package com.example.demo.web.controller;

import com.example.demo.service.KeyContentService;
import com.example.demo.web.models.content.KeyContent;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/content")
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
    public List<KeyContent> getContentByKey(@PathVariable String key) {
        return service.getContents(key);
    }
}
