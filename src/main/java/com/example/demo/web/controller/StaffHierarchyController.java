package com.example.demo.web.controller;

import com.example.demo.service.StaffHierarchyService;
import com.example.demo.web.models.staff.Staff;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hierarchy")
public class StaffHierarchyController {

    private final StaffHierarchyService staffHierarchyService;

    public StaffHierarchyController(StaffHierarchyService staffHierarchyService) {
        this.staffHierarchyService = staffHierarchyService;
    }

    // 1. Get all Heads
    @GetMapping("/heads")
       @CrossOrigin("*")
    public List<Staff> getAllHeads() {
        return staffHierarchyService.getAllHeads();
    }

    // 2. Get staff under a specific Head
    @GetMapping("/head/{headId}/staff")
       @CrossOrigin("*")
    public List<Staff> getStaffUnderHead(@PathVariable Long headId) {
        return staffHierarchyService.getStaffUnderHead(headId);
    }
}
