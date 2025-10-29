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
            @RequestParam(required = false) String division) {
        return ResponseEntity.ok(service.getEmployees(isDirector, subDeptId, division));
    }
}
