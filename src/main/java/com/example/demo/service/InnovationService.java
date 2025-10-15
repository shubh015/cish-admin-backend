package com.example.demo.service;


import com.example.demo.repository.InnovationRepository;
import com.example.demo.web.models.Innovation;

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
}
