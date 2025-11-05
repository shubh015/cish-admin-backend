package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.web.models.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ✅ Dynamic filter query using optional parameters
    @Query("""
           SELECT e FROM Employee e
           WHERE (:isDirector IS NULL OR e.isDirector = :isDirector)
             AND (:subDeptId IS NULL OR e.subDeptId = :subDeptId)
             AND (:division IS NULL OR e.division = :division)
           """)
    List<Employee> findByFilters(@Param("isDirector") Boolean isDirector,
                                 @Param("subDeptId") String subDeptId,
                                 @Param("division") String division);

    // ✅ Published=false & Active=true version
    @Query("""
           SELECT e FROM Employee e
           WHERE (:isDirector IS NULL OR e.isDirector = :isDirector)
             AND (:subDeptId IS NULL OR e.subDeptId = :subDeptId)
             AND (:division IS NULL OR e.division = :division)
             AND e.isPublished = false
             AND e.isActive = true
           """)
    List<Employee> findByFiltersAndIspublishedFalseAndIsactiveTrue(@Param("isDirector") Boolean isDirector,
                                                    @Param("subDeptId") String subDeptId,
                                                    @Param("division") String division);

    // ✅ Back to creator = true version
    @Query("""
           SELECT e FROM Employee e
           WHERE (:isDirector IS NULL OR e.isDirector = :isDirector)
             AND (:subDeptId IS NULL OR e.subDeptId = :subDeptId)
             AND (:division IS NULL OR e.division = :division)
             AND e.backToCreator = true
           """)
    List<Employee> findByFiltersAndBacktocreatorTrue(@Param("isDirector") Boolean isDirector,
                                                @Param("subDeptId") String subDeptId,
                                                @Param("division") String division);

    // ✅ Published=true & Active=true version
    @Query("""
           SELECT e FROM Employee e
           WHERE (:isDirector IS NULL OR e.isDirector = :isDirector)
             AND (:subDeptId IS NULL OR e.subDeptId = :subDeptId)
             AND (:division IS NULL OR e.division = :division)
             AND e.isPublished = true
             AND e.isActive = true
           """)
    List<Employee> findByFiltersIspublishedTrueAndIsactiveTrue(@Param("isDirector") Boolean isDirector,
                                                  @Param("subDeptId") String subDeptId,
                                                  @Param("division") String division);

    @Query("""
       SELECT e FROM Employee e
       WHERE (:isDirector IS NULL OR e.isDirector = :isDirector)
         AND (:subDeptId IS NULL OR e.subDeptId = :subDeptId)
         AND (:division IS NULL OR e.division = :division)
         AND e.backToCreator = true
         AND e.isActive = true
       """)
List<Employee> findByFiltersAndBacktocreatorTrueAndIsactiveTrue(
        @Param("isDirector") Boolean isDirector,
        @Param("subDeptId") String subDeptId,
        @Param("division") String division);

}
