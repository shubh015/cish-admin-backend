package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.web.models.Employee;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> saveAll(List<Employee> employees) {
        return repository.saveAll(employees);
    }

    public List<Employee> getEmployees(Boolean isDirector, String subDeptId, String division, String role) {

        if(role != null ){
            if(role.equalsIgnoreCase("admin"))
               return repository.findByFiltersAndIspublishedFalseAndIsactiveTrue(isDirector, subDeptId, division);
            if(role.equalsIgnoreCase("creator"))
                return repository.findByFiltersAndBacktocreatorTrueAndIsactiveTrue(isDirector, subDeptId, division);     
        }

        return repository.findByFiltersIspublishedTrueAndIsactiveTrue(isDirector, subDeptId, division);
    }


      @Transactional
public void updateStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
    for (Long id : ids) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innovation not found with ID: " + id));

        if (isPublished != null) emp.setIsPublished(isPublished);;
        if (isActive != null) emp.setIsActive(isActive);
        if (backToCreator != null) emp.setBackToCreator(backToCreator);

        repository.save(emp);
    }
}
}
