package com.example.demo.service;


import com.example.demo.web.models.event.NewsEvent;
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

    public List<NewsEvent> getByType(String type) {
        return repo.findByType(type);
    }
}
