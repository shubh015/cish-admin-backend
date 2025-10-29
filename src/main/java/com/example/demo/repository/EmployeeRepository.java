package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.web.models.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
           SELECT e FROM Employee e
           WHERE (:isDirector IS NULL OR e.isDirector = :isDirector)
             AND (:subDeptId IS NULL OR e.subDeptId = :subDeptId)
             AND (:division IS NULL OR e.division = :division)
           """)
    List<Employee> findByFilters(@Param("isDirector") Boolean isDirector,
                                 @Param("subDeptId") String subDeptId,
                                 @Param("division") String division);
}
