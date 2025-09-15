package com.example.demo.web.models.tchnology;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "technology_attachment")
public class TechnologyAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

    private String filename;
    private String url;
    private String mimeType;
    private Long sizeBytes;
    private Instant uploadedAt = Instant.now();

    // getters/setters...
}
