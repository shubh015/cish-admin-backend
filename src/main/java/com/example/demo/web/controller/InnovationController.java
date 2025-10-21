package com.example.demo.web.controller;

import com.example.demo.service.InnovationService;
import com.example.demo.web.models.Innovation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/innovation")
@CrossOrigin("*")

public class InnovationController {

    private final InnovationService service;

    public InnovationController(InnovationService service) {
        this.service = service;
    }

    @PostMapping
    @CrossOrigin("*")
    public String saveInnovation(@RequestBody Map<String, List<Innovation>> payload) {
        List<Innovation> toSave = new ArrayList<>();

        if (payload.containsKey("technology")) {
            payload.get("technology").forEach(t -> t.setType("TECHNOLOGY"));
            toSave.addAll(payload.get("technology"));
        } else if (payload.containsKey("varities")) {
            payload.get("varities").forEach(v -> v.setType("VARIETY"));
            toSave.addAll(payload.get("varities"));
        } else {
            return "❌ Invalid request: Expected key 'technology' or 'varities'";
        }

        service.saveAll(toSave);
        return "✅ " + toSave.size() + " " + toSave.get(0).getType() + " records saved successfully.";
    }

    @GetMapping
    @CrossOrigin("*")
    public List<Innovation> getInnovations(@RequestParam("key") String key) {
        String type;
        if (key.equalsIgnoreCase("technology")) {
            type = "TECHNOLOGY";
        } else if (key.equalsIgnoreCase("varities")) {
            type = "VARIETY";
        } else {
            throw new IllegalArgumentException("Invalid key. Use 'technology' or 'varities'.");
        }

        return service.getByType(type);
    }

    @PostMapping("/status")
    @CrossOrigin("*")
    public ResponseEntity<String> updateInnovationStatus(@RequestBody Map<String, Object> payload) {
        try {
            String key = (String) payload.get("key");
            if (key == null || !payload.containsKey("ids")) {
                return ResponseEntity.badRequest().body("❌ Missing 'key' or 'ids' in request.");
            }

            // ✅ safely convert ids to List<Long>
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
