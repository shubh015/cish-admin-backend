package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.content.KeyContent;

public interface KeyContentRepository extends JpaRepository<KeyContent, Long> {
    List<KeyContent> findByContentKey(String contentKey);

    List<KeyContent> findByContentKeyAndIspublishedTrueAndIsactiveTrue(String type);
    List<KeyContent> findByContentKeyAndIspublishedFalseAndIsactiveTrue(String type);
    List<KeyContent> findByContentKeyAndBacktocreatorTrue(String type);

    List<KeyContent> findByContentKeyAndBacktocreatorTrueAndIsactiveTrue(String key);

    List<KeyContent> findByContentKeyAndIspublishedFalseAndIsactiveTrueAndBacktocreatorFalse(String key);
}
