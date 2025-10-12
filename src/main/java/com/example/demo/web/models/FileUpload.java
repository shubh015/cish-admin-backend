package com.example.demo.web.models;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "file_upload")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileUrl;
    private String description;
    private Boolean isActive = true;
    private Boolean isDirector = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
