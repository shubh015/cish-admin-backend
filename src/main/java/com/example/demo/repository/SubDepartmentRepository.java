package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.SubDepartment;

public interface SubDepartmentRepository extends JpaRepository<SubDepartment, Integer> {
}

