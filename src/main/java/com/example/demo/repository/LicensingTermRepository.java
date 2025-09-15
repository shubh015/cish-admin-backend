package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.tchnology.LicensingTerm;

public interface LicensingTermRepository extends JpaRepository<LicensingTerm, Long> {
}

