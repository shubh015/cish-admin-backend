package com.example.demo.web.controller;



import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.EmployeeService;
import com.example.demo.web.models.Employee;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService service;

    // ✅ Save API (POST)
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveEmployees(@RequestBody Map<String, List<Employee>> payload) {
        List<Employee> saved = service.saveAll(payload.get("employees"));
        return ResponseEntity.ok(Map.of(
                "message", "Employees saved successfully",
                "count", saved.size(),
                "data", saved
        ));
    }

    // ✅ Get API (GET) - filter by isDirector, subDeptId, division
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(
            @RequestParam(required = false) Boolean isDirector,
            @RequestParam(required = false) String subDeptId,
            @RequestParam(required = false) String division,
             @RequestParam(value = "role", required = false) String role) {
        return ResponseEntity.ok(service.getEmployees(isDirector, subDeptId, division,role));
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
