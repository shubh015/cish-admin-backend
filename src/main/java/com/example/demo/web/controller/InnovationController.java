package com.example.demo.web.controller;


import com.example.demo.service.InnovationService;
import com.example.demo.web.models.Innovation;

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
}

