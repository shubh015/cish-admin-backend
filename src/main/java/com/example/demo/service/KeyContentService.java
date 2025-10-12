package com.example.demo.service;


import com.example.demo.repository.KeyContentRepository;
import com.example.demo.web.models.content.KeyContent;

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
}
