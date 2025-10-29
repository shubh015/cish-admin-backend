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

    public List<Employee> getEmployees(Boolean isDirector, String subDeptId, String division) {
        return repository.findByFilters(isDirector, subDeptId, division);
    }
}
