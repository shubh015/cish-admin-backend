package com.example.demo.web.controller;

import com.example.demo.service.SubDeptService;
import com.example.demo.web.models.StaffMember;
import com.example.demo.web.models.SubDepartment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subdepartments")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SubDepartmentController {

    private final SubDeptService subDeptService;

   @PostMapping
@CrossOrigin("*")
public ResponseEntity<?> createOrUpdateSubDepartment(@RequestBody SubDepartment subDept) {

    SubDepartment existing = subDeptService.findBySubDeptId(subDept.getSubDeptId(), subDept.getDepartment());

    if (existing != null) {
        // ✅ Add employees to existing sub-department
        if (subDept.getEmployees() != null && !subDept.getEmployees().isEmpty()) {
            for (StaffMember emp : subDept.getEmployees()) {

                // ✅ If emp has an ID, fetch managed reference
                if (emp.getId() != null) {
                    StaffMember managedEmp = subDeptService.findStaffById(emp.getId());
                    managedEmp.setSubDepartment(existing);
                    if (emp.getName().equalsIgnoreCase(existing.getSubDeptName())) {
                        managedEmp.setIsHead(true);
                    }
                    subDeptService.saveEmployee(managedEmp);
                } else {
                    // New employee — can be persisted directly
                    emp.setSubDepartment(existing);
                    if (emp.getName().equalsIgnoreCase(existing.getSubDeptName())) {
                        emp.setIsHead(true);
                    }
                    subDeptService.saveEmployee(emp);
                }
            }
        }

        return ResponseEntity.ok("✅ Existing SubDepartment found. Added "
                + subDept.getEmployees().size() + " employees successfully.");

    } else {
        // ✅ Create new SubDepartment
        if (subDept.getEmployees() != null && !subDept.getEmployees().isEmpty()) {
            for (StaffMember emp : subDept.getEmployees()) {
                emp.setSubDepartment(subDept);
                if (emp.getName().equalsIgnoreCase(subDept.getSubDeptName())) {
                    emp.setIsHead(true);
                }
            }
        }

        SubDepartment saved = subDeptService.saveSubDepartment(subDept);
        return ResponseEntity.ok("✅ New SubDepartment created with "
                + (saved.getEmployees() != null ? saved.getEmployees().size() : 0)
                + " employees.");
    }
}

    @GetMapping
    @CrossOrigin("*")
    public ResponseEntity<List<SubDepartment>> getAll(@RequestParam(value = "department", required = false) String department,
    @RequestParam(value = "role", required = false) String role) {
        return ResponseEntity.ok(subDeptService.getAll(department,role));
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<SubDepartment> getById(@PathVariable Long id) {
        SubDepartment subDept = subDeptService.getById(id);
        return subDept != null ? ResponseEntity.ok(subDept) : ResponseEntity.notFound().build();
    }

    @PostMapping("/status")
    @CrossOrigin("*")
    public ResponseEntity<String> updateStaffMemberStatus(@RequestBody Map<String, Object> payload) {
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
                case "publish" -> subDeptService.updateStatus(ids, true, true, false);
                case "delete" -> subDeptService.updateStatus(ids, null, false, null);
                case "backtocreator" -> subDeptService.updateStatus(ids, false, null, true);
                default -> {
                    return ResponseEntity.badRequest()
                            .body("❌ Invalid key. Use 'publish', 'delete', or 'backToCreator'.");
                }
            }

            return ResponseEntity.ok("✅ Status updated for " + ids.size() + " staff record(s) with action: " + key);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error: " + e.getMessage());
        }
    }
}
