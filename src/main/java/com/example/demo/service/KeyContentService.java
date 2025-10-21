package com.example.demo.service;


import com.example.demo.repository.KeyContentRepository;
import com.example.demo.web.models.Innovation;
import com.example.demo.web.models.content.KeyContent;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KeyContentService {

    private final KeyContentRepository repository;

    public KeyContentService(KeyContentRepository repository) {
        this.repository = repository;
    }

    public void saveContents(String key, List<KeyContent> contents) {
        contents.forEach(c -> c.setContentKey(key));
        repository.saveAll(contents);
    }

    public List<KeyContent> getContents(String key) {
        return repository.findByContentKey(key);
    }

    @Transactional
public void updateStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
    for (Long id : ids) {
        KeyContent innovation = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innovation not found with ID: " + id));

        if (isPublished != null) innovation.setIspublished(isPublished);
        if (isActive != null) innovation.setIsactive(isActive);
        if (backToCreator != null) innovation.setBacktocreator(backToCreator);

        repository.save(innovation);
    }
}
}
