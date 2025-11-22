package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.media.MediaFile;

import java.util.List;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
    List<MediaFile> findByType(String type);

    List<MediaFile> findByTypeIgnoreCaseAndIspublishedFalseAndIsactiveTrue(String type);

    List<MediaFile> findByTypeIgnoreCaseAndIspublishedTrueAndIsactiveTrue(String type);

    List<MediaFile> findByTypeIgnoreCaseAndBacktocreatorTrue(String type);

    List<MediaFile> findByTypeIgnoreCaseAndBacktocreatorTrueAndIsactiveTrue(String type);

    List<MediaFile> findByTypeIgnoreCaseAndIspublishedFalseAndIsactiveTrueAndBacktocreatorFalse(String type);
}
