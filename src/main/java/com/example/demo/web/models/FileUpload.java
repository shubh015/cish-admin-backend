package com.example.demo.web.models;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_upload")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_director")
    private Boolean isDirector = false;

    @Column(name = "isimage")
    private Boolean isImage = false;

    @Column(name = "isvideo")
    private Boolean isVideo = false;

    @Column(name = "isdgca")
    private Boolean isDgca = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
