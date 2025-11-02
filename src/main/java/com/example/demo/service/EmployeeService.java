package com.example.demo.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.web.models.Employee;

import java.util.List;

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
                return repository.findByFiltersAndBacktocreatorTrue(isDirector, subDeptId, division);     
        }

        return repository.findByFiltersIspublishedTrueAndIsactiveTrue(isDirector, subDeptId, division);
    }
}
