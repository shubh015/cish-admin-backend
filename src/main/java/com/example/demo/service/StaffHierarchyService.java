package com.example.demo.service;


import com.example.demo.repository.StaffRepository;
import com.example.demo.web.models.staff.Staff;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffHierarchyService {

    private final StaffRepository staffRepository;

    public StaffHierarchyService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllHeads() {
        return staffRepository.findByDesignation_DesignationNameIgnoreCase("Head");
    }

    public List<Staff> getStaffUnderHead(Long headId) {
        return staffRepository.findByReportingOfficer_StaffId(headId);
    }
}

