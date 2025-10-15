package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.Innovation;

import java.util.List;

public interface InnovationRepository extends JpaRepository<Innovation, Long> {
    List<Innovation> findByTypeIgnoreCase(String type);
}