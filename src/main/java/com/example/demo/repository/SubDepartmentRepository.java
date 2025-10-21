package com.example.demo.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.SubDepartment;

public interface SubDepartmentRepository extends JpaRepository<SubDepartment, Long> {
    Optional<SubDepartment> findBySubDeptId(String subDeptId);
}

