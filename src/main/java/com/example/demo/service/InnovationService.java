package com.example.demo.service;


import com.example.demo.repository.InnovationRepository;
import com.example.demo.web.models.Innovation;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InnovationService {

    private final InnovationRepository repository;

    public InnovationService(InnovationRepository repository) {
        this.repository = repository;
    }

    public void saveAll(List<Innovation> list) {
        repository.saveAll(list);
    }

    public List<Innovation> getByType(String type) {
        return repository.findByTypeIgnoreCase(type);
    }


    @Transactional
public void updateStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
    for (Long id : ids) {
        Innovation innovation = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innovation not found with ID: " + id));

        if (isPublished != null) innovation.setIspublished(isPublished);
        if (isActive != null) innovation.setIsactive(isActive);
        if (backToCreator != null) innovation.setBacktocreator(backToCreator);

        repository.save(innovation);
    }
}

}
