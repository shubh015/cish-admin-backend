package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.staff.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

     // 1. Find all heads
    List<Staff> findByDesignation_DesignationNameIgnoreCase(String designationName);

    // 2. Find staff under a specific head
    List<Staff> findByReportingOfficer_StaffId(Long reportingOfficerId);
}
