package com.example.demo.service;


import com.example.demo.repository.StaffRepository;
import com.example.demo.web.models.staff.Staff;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(Long id, Staff updatedStaff) {
        return staffRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(updatedStaff.getFullName());
                    existing.setEmail(updatedStaff.getEmail());
                    existing.setPhoneNumber(updatedStaff.getPhoneNumber());
                    existing.setProfilePicUrl(updatedStaff.getProfilePicUrl());
                    existing.setDesignation(updatedStaff.getDesignation());
                    existing.setDivision(updatedStaff.getDivision());
                    existing.setCategory(updatedStaff.getCategory());
                    existing.setReportingOfficer(updatedStaff.getReportingOfficer());
                    existing.setUpdatedAt(java.time.LocalDateTime.now());
                    return staffRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Staff not found with id " + id));
    }

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
