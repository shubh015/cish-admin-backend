package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.ExternalProject;

public interface ExternalProjectRepository extends JpaRepository<ExternalProject, Long> {



      List<ExternalProject> findAllByIspublishedFalseAndIsactiveTrue();

    List<ExternalProject> findAllByBacktocreatorTrue();

    List<ExternalProject> findAllByIspublishedTrueAndIsactiveTrue();

    List<ExternalProject> findAllByBacktocreatorTrueAndIsactiveTrue();
}
