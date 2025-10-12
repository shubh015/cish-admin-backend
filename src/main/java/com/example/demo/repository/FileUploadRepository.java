package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.FileUpload;

import java.util.List;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    List<FileUpload> findByIsDirector(boolean isDirector);
}
