package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.HouseProject;

public interface HouseProjectRepository extends JpaRepository<HouseProject, Long> {

     List<HouseProject> findAllByIspublishedFalseAndIsactiveTrue();

    List<HouseProject> findAllByBacktocreatorTrue();

    List<HouseProject> findAllByIspublishedTrueAndIsactiveTrue();
}

