package com.example.demo.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.web.models.SubDepartment;

public interface SubDepartmentRepository extends JpaRepository<SubDepartment, Long> {
    Optional<SubDepartment> findBySubDeptIdAndDepartment(String subDeptId, String department);


     // ✅ Fetch sub-departments by department, include only active & published staff
    @Query("""
        SELECT DISTINCT sd
        FROM SubDepartment sd
        LEFT JOIN FETCH sd.employees e
        WHERE sd.department = :department
          AND (e.ispublished = true AND e.isactive = true)
        """)
    List<SubDepartment> findByDepartmentWithActivePublishedEmployees(@Param("department") String department);



        @Query("""
        SELECT DISTINCT sd
        FROM SubDepartment sd
        LEFT JOIN FETCH sd.employees e
        WHERE sd.department = :department
          AND (e.ispublished = false AND e.isactive = true)
        """)
    List<SubDepartment> findByDepartmentWithcreatedEmployees(@Param("department") String department);



        @Query("""
        SELECT DISTINCT sd
        FROM SubDepartment sd
        LEFT JOIN FETCH sd.employees e
        WHERE sd.department = :department
          AND (e.backtocreator = true)
        """)
    List<SubDepartment> findByDepartmentWithBacktoCreatedEmployees(@Param("department") String department);
}

