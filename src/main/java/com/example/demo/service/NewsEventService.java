package com.example.demo.service;


import com.example.demo.web.models.Innovation;
import com.example.demo.web.models.event.NewsEvent;

import jakarta.transaction.Transactional;

import com.example.demo.repository.NewsEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsEventService {

    private final NewsEventRepository repo;

    public NewsEventService(NewsEventRepository repo) {
        this.repo = repo;
    }

    public NewsEvent save(NewsEvent e) {
        return repo.save(e);
    }

    public List<NewsEvent> saveAll(List<NewsEvent> list) {
        return repo.saveAll(list);
    }

    public List<NewsEvent> getByType(String type, String role) {

        if(role != null ){
            if(role.equalsIgnoreCase("admin"))
               return repo.findByTypeIgnoreCaseAndIspublishedFalseAndIsactiveTrue(type);
            if(role.equalsIgnoreCase("creater"))
                return repo.findByTypeIgnoreCaseAndBacktocreatorTrue(type);     
        }
        return repo.findByTypeIgnoreCaseAndIspublishedTrueAndIsactiveTrue(type);
    }

    @Transactional
public void updateStatus(List<Long> ids, Boolean isPublished, Boolean isActive, Boolean backToCreator) {
    for (Long id : ids) {
        NewsEvent innovation = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Innovation not found with ID: " + id));

        if (isPublished != null) innovation.setIspublished(isPublished);
        if (isActive != null) innovation.setIsactive(isActive);
        if (backToCreator != null) innovation.setBacktocreator(backToCreator);

        repo.save(innovation);
    }
}
}
