package com.example.demo.service;


import com.example.demo.web.models.HouseProject;
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
               return externalRepo.findAllByIspublishedFalseAndIsactiveTrue();
            if(role.equalsIgnoreCase("creator"))
                return externalRepo.findAllByBacktocreatorTrue();     
        }
        return externalRepo.findAllByIspublishedTrueAndIsactiveTrue();
    }
}
