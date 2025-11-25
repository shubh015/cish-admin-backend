package com.example.demo.service;


import com.example.demo.web.models.HouseProject;

import jakarta.transaction.Transactional;

import com.example.demo.web.models.ExternalProject;
import com.example.demo.repository.HouseProjectRepository;
import com.example.demo.repository.ExternalProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final HouseProjectRepository houseRepo;
    private final ExternalProjectRepository externalRepo;

    public HouseProject saveHouseProject(HouseProject project) {
        return houseRepo.save(project);
    }

    public ExternalProject saveExternalProject(ExternalProject project) {
        return externalRepo.save(project);
    }

    public List<HouseProject> getAllHouseProjects(String role) {
         if(role != null ){
            if(role.equalsIgnoreCase("admin"))
               return houseRepo.findAllByIspublishedFalseAndIsactiveTrue();
            if(role.equalsIgnoreCase("creator"))
                return houseRepo.findAllByBacktocreatorTrue();     
        }
        return houseRepo.findAllByIspublishedTrueAndIsactiveTrue();
    }

    public List<ExternalProject> getAllExternalProjects(String role) {
         if(role != null ){
            if(role.equalsIgnoreCase("admin"))
               return externalRepo.findAllByIspublishedFalseAndIsactiveTrueAndBacktocreatorFalse();
            if(role.equalsIgnoreCase("creator"))
                return externalRepo.findAllByBacktocreatorTrueAndIsactiveTrue();     
        }
        return externalRepo.findAllByIspublishedTrueAndIsactiveTrue();
    }


      // ✅ Update House Project Status
    @Transactional
    public void updateHouseProjectStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
        for (Long id : ids) {
            HouseProject project = houseRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("House Project not found with ID: " + id));

            if (isPublished != null) project.setIspublished(isPublished);
            if (isActive != null) project.setIsactive(isActive);
            if (backToCreator != null) project.setBacktocreator(backToCreator);

            houseRepo.save(project);
        }
    }

    // ✅ Update External Project Status
    @Transactional
    public void updateExternalProjectStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
        for (Long id : ids) {
            ExternalProject project = externalRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("External Project not found with ID: " + id));

            if (isPublished != null) project.setIspublished(isPublished);
            if (isActive != null) project.setIsactive(isActive);
            if (backToCreator != null) project.setBacktocreator(backToCreator);

            externalRepo.save(project);
        }
    }


    @Transactional
    public void updateProjectStatusByIds(List<Long> ids, Boolean isPublished, Boolean isActive,
                                         Boolean backToCreator, String remark) {

        for (Long id : ids) {

            // --- Try House Project ---
            houseRepo.findById(id).ifPresent(project -> {
                if (isPublished != null) project.setIspublished(isPublished);
                if (isActive != null) project.setIsactive(isActive);
                if (backToCreator != null) project.setBacktocreator(backToCreator);
               // if (remark != null) project.setRemark(remark);
                houseRepo.save(project);
            });

            // --- Try External Project ---
            externalRepo.findById(id).ifPresent(project -> {
                if (isPublished != null) project.setIspublished(isPublished);
                if (isActive != null) project.setIsactive(isActive);
                if (backToCreator != null) project.setBacktocreator(backToCreator);
               // if (remark != null) project.setRemark(remark);
                externalRepo.save(project);
            });
        }
    }
}



