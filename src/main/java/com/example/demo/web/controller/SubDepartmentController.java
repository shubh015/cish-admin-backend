package com.example.demo.web.controller;


import com.example.demo.service.SubDepartmentService;
import com.example.demo.web.models.StaffMember;
import com.example.demo.web.models.SubDepartment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subdepartments")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SubDepartmentController {

    private final SubDepartmentService subDeptService;

    @PostMapping
    @CrossOrigin("*")
    public ResponseEntity<SubDepartment> createSubDepartment(@RequestBody SubDepartment subDept) {
        // Identify "head" from employees and mark isHead=true
        if (subDept.getEmployees() != null && !subDept.getEmployees().isEmpty()) {
            for (StaffMember emp : subDept.getEmployees()) {
                if (emp.getName().equalsIgnoreCase(subDept.getSubDeptName())) {
                    emp.setIsHead(true);
                }
            }
        }
        SubDepartment saved = subDeptService.saveSubDepartment(subDept);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    @CrossOrigin("*")
    public ResponseEntity<List<SubDepartment>> getAll() {
        return ResponseEntity.ok(subDeptService.getAll());
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<SubDepartment> getById(@PathVariable Integer id) {
        SubDepartment subDept = subDeptService.getById(id);
        return subDept != null ? ResponseEntity.ok(subDept) : ResponseEntity.notFound().build();
    }
}
