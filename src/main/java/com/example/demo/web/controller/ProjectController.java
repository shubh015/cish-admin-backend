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
    @CrossOrigin("*")
    public ResponseEntity<String> saveProject(@RequestBody Map<String, Object> payload) {
        
        if (payload.containsKey("houseProject")) {
            Map<String, Object> data = (Map<String, Object>) payload.get("houseProject");
            HouseProject project = new HouseProject();
            project.setId(data.get("id") != null ? Long.valueOf(data.get("id").toString()) : null);
            project.setBacktocreator(data.get("backtocreator") != null ? Boolean.parseBoolean(data.get("backtocreator").toString()) : false);
            project.setActivityName((String) data.get("activityName"));
            project.setPrincipalInvestigator((String) data.get("principalInvestigator"));
            project.setCoPrincipalInvestigator((String) data.get("coPrincipalInvestigator"));
            project.setCreatedby((String) data.get("createdby"));
            projectService.saveHouseProject(project);
            return ResponseEntity.ok("✅ House Project saved successfully.");
        }
        else if (payload.containsKey("ExternalProject")) {
            Map<String, Object> data = (Map<String, Object>) payload.get("ExternalProject");
            ExternalProject project = new ExternalProject();
            project.setId(data.get("id") != null ? Long.valueOf(data.get("id").toString()) : null);
            project.setBacktocreator(data.get("backtocreator") != null ? Boolean.parseBoolean(data.get("backtocreator").toString()) : false);
            project.setProjectTitle((String) data.get("projectTitle"));
            project.setPiOffice((String) data.get("piOffice"));
            project.setStartDate(java.time.LocalDate.parse((String) data.get("startDate")));
            project.setEndDate(java.time.LocalDate.parse((String) data.get("endDate")));
            project.setCreatedby((String) data.get("createdby"));
            projectService.saveExternalProject(project);
            return ResponseEntity.ok("✅ External Project saved successfully.");
        }
        return ResponseEntity.badRequest().body("❌ Invalid request: Expected key 'houseProject' or 'ExternalProject'.");
    }

    // ✅ Get All House Projects
    @GetMapping("/house")
    @CrossOrigin("*")
    public ResponseEntity<List<HouseProject>> getAllHouseProjects(@RequestParam(value = "role", required = false) String role) {
        return ResponseEntity.ok(projectService.getAllHouseProjects(role));
    }

    // ✅ Get All External Projects
    @GetMapping("/external")
    @CrossOrigin("*")
    public ResponseEntity<List<ExternalProject>> getAllExternalProjects( @RequestParam(value = "role", required = false) String role) {
        return ResponseEntity.ok(projectService.getAllExternalProjects(role));
    }
@PostMapping("/status")
@CrossOrigin("*")
public ResponseEntity<String> updateProjectStatus(@RequestBody Map<String, Object> payload) {
    try {
        String key = (String) payload.get("key");
        Object remarkObj = payload.get("remark");
        String remark = remarkObj != null ? remarkObj.toString() : null;

        if (key == null || !payload.containsKey("ids")) {
            return ResponseEntity.badRequest()
                    .body("❌ Missing 'key' or 'ids' in request.");
        }

        // Convert IDs safely
        List<Long> ids = ((List<?>) payload.get("ids"))
                .stream()
                .map(id -> Long.valueOf(String.valueOf(id)))
                .toList();

        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ 'ids' list cannot be empty.");
        }

        // Status flags
        Boolean isPublished = null;
        Boolean isActive = null;
        Boolean backToCreator = null;

        switch (key.toLowerCase()) {
            case "publish" -> {
                isPublished = true;
                isActive = true;
                backToCreator = false;
            }
            case "delete" -> {
                isPublished = null;
                isActive = false;
                backToCreator = null;
            }
            case "backtocreator" -> {
                isPublished = false;
                isActive = null;
                backToCreator = true;
            }
            default -> {
                return ResponseEntity.badRequest()
                        .body("❌ Invalid key. Use 'publish', 'delete', or 'backToCreator'.");
            }
        }

        // 🔥 Single method that updates both House + External if ID exists
        projectService.updateProjectStatusByIds(ids, isPublished, isActive, backToCreator, remark);

        return ResponseEntity.ok("✅ Status updated for " + ids.size() + " record(s).");

    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("❌ Error: " + e.getMessage());
    }
}



}

