package com.example.demo.web.models;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "external_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectTitle;
    private String piOffice;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();



    // ✅ Newly added flags
    @Builder.Default
    @Column(name = "ispublished", nullable = false)
    private Boolean ispublished = false;

    @Builder.Default
    @Column(name = "isactive", nullable = false)
    private Boolean isactive = true;

    @Builder.Default
    @Column(name = "backtocreator", nullable = false)
    private Boolean backtocreator = false;
}
