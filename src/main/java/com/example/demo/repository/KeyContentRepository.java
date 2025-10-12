package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.content.KeyContent;

import java.util.List;

public interface KeyContentRepository extends JpaRepository<KeyContent, Long> {
    List<KeyContent> findByContentKey(String contentKey);
}
