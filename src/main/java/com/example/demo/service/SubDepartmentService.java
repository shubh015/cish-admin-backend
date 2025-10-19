package com.example.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.SubDepartmentRepository;
import com.example.demo.web.models.SubDepartment;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubDepartmentService {

    private final SubDepartmentRepository subDeptRepo;

    @Transactional
    public SubDepartment saveSubDepartment(SubDepartment subDept) {
        // Set relationships
        if (subDept.getEmployees() != null) {
            subDept.getEmployees().forEach(emp -> emp.setSubDepartment(subDept));
        }
        return subDeptRepo.save(subDept);
    }

    public List<SubDepartment> getAll() {
        return subDeptRepo.findAll();
    }

    public SubDepartment getById(Integer id) {
        return subDeptRepo.findById(id).orElse(null);
    }
}
