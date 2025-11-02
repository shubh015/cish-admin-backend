package com.example.demo.web.controller;


import com.example.demo.service.ProjectService;
import com.example.demo.web.models.HouseProject;
import com.example.demo.web.models.ExternalProject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    // ✅ Save API for both keys
    @PostMapping
    public ResponseEntity<String> saveProject(@RequestBody Map<String, Object> payload) {
        
        if (payload.containsKey("houseProject")) {
            Map<String, Object> data = (Map<String, Object>) payload.get("houseProject");
            HouseProject project = new HouseProject();
            project.setActivityName((String) data.get("activityName"));
            project.setPrincipalInvestigator((String) data.get("principalInvestigator"));
            project.setCoPrincipalInvestigator((String) data.get("coPrincipalInvestigator"));
            projectService.saveHouseProject(project);
            return ResponseEntity.ok("✅ House Project saved successfully.");
        }
        else if (payload.containsKey("ExternalProject")) {
            Map<String, Object> data = (Map<String, Object>) payload.get("ExternalProject");
            ExternalProject project = new ExternalProject();
            project.setProjectTitle((String) data.get("projectTitle"));
            project.setPiOffice((String) data.get("piOffice"));
            project.setStartDate(java.time.LocalDate.parse((String) data.get("startDate")));
            project.setEndDate(java.time.LocalDate.parse((String) data.get("endDate")));
            projectService.saveExternalProject(project);
            return ResponseEntity.ok("✅ External Project saved successfully.");
        }
        return ResponseEntity.badRequest().body("❌ Invalid request: Expected key 'houseProject' or 'ExternalProject'.");
    }

    // ✅ Get All House Projects
    @GetMapping("/house")
    public ResponseEntity<List<HouseProject>> getAllHouseProjects(@RequestParam(value = "role", required = false) String role) {
        return ResponseEntity.ok(projectService.getAllHouseProjects(role));
    }

    // ✅ Get All External Projects
    @GetMapping("/external")
    public ResponseEntity<List<ExternalProject>> getAllExternalProjects( @RequestParam(value = "role", required = false) String role) {
        return ResponseEntity.ok(projectService.getAllExternalProjects(role));
    }



@PostMapping("/status")
@CrossOrigin("*")
public ResponseEntity<String> updateProjectStatus(@RequestBody Map<String, Object> payload) {
    try {
        String key = (String) payload.get("key");
        String projectType = (String) payload.get("type"); // "house" or "external"

        if (key == null || projectType == null || !payload.containsKey("ids")) {
            return ResponseEntity.badRequest()
                    .body("❌ Missing 'key', 'type', or 'ids' in request.");
        }

        // ✅ Safely convert ids
        List<Long> ids = ((List<?>) payload.get("ids"))
                .stream()
                .map(id -> Long.valueOf(String.valueOf(id)))
                .toList();

        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ 'ids' list cannot be empty.");
        }

        // ✅ Determine update flags
        Boolean isPublished = null, isActive = null, backToCreator = null;

        switch (key.toLowerCase()) {
            case "publish" -> { isPublished = true; isActive = true; backToCreator = false; }
            case "delete" -> { isPublished = null; isActive = false; backToCreator = null; }
            case "backtocreator" -> { isPublished = false; isActive = null; backToCreator = true; }
            default -> {
                return ResponseEntity.badRequest()
                        .body("❌ Invalid key. Use 'publish', 'delete', or 'backToCreator'.");
            }
        }

        // ✅ Route based on type
        switch (projectType.toLowerCase()) {
            case "house" -> projectService.updateHouseProjectStatus(ids, isPublished, isActive, backToCreator);
            case "external" -> projectService.updateExternalProjectStatus(ids, isPublished, isActive, backToCreator);
            default -> {
                return ResponseEntity.badRequest().body("❌ Invalid type. Use 'house' or 'external'.");
            }
        }

        return ResponseEntity.ok("✅ Status updated for " + ids.size() + " " + projectType + " project(s) with action: " + key);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("❌ Error: " + e.getMessage());
    }
}

}

