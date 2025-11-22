package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.StaffMemberRepository;
import com.example.demo.repository.SubDepartmentRepository;
import com.example.demo.web.models.StaffMember;
import com.example.demo.web.models.SubDepartment;
import com.example.demo.web.models.content.KeyContent;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubDeptService {

    private final SubDepartmentRepository subDeptRepo;
    private final StaffMemberRepository staffRepo;

    public SubDepartment findBySubDeptId(String subDeptId, String department) {
        return subDeptRepo.findBySubDeptIdAndDepartment(subDeptId,department).orElse(null);
    }

    public SubDepartment saveSubDepartment(SubDepartment subDept) {
        return subDeptRepo.save(subDept);
    }

    public void saveEmployees(List<StaffMember> employees) {
        staffRepo.saveAll(employees);
    }

    public List<SubDepartment> getAll(String department, String role) {
        if(department == null || department.isEmpty()){
            return subDeptRepo.findAll();
        }
        if(role != null ){
            if(role.equalsIgnoreCase("admin"))
               return subDeptRepo.findByDepartmentWithcreatedEmployeesAndBacktocreatorFalse(department);
            if(role.equalsIgnoreCase("creator"))
                return subDeptRepo.findByDepartmentWithBacktoCreatedEmployees(department);     
        }
        return subDeptRepo.findByDepartmentWithActivePublishedEmployees(department);
    }

    public SubDepartment getById(Long id) {
        return subDeptRepo.findById(id).orElse(null);
    }

    @Transactional
    public void updateStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
        for (Long id : ids) {
            StaffMember innovation = staffRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Innovation not found with ID: " + id));

            if (isPublished != null)
                innovation.setIspublished(isPublished);
            if (isActive != null)
                innovation.setIsactive(isActive);
            if (backToCreator != null)
                innovation.setBacktocreator(backToCreator);

            staffRepo.save(innovation);
        }
    }




    public StaffMember findStaffById(Integer id) {
    return staffRepo.findById(id.longValue()).orElseThrow(() -> new RuntimeException("Staff not found"));
}

public void saveEmployee(StaffMember emp) {
    staffRepo.save(emp);
}

}
