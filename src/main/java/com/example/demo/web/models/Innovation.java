package com.example.demo.web.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "innovation")
public class Innovation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;  // TECHNOLOGY or VARIETY
    private String title;
    private String date; // <-- changed from LocalDate to String
    private String image;
    private String inventor;
    private String collaborators;
    private String maintainerInventor;

    private String yearOfDevelopment;
    private String yearOfRelease;
    private String yearOfCommercialization;

    private String icNumber;
    private String ppvfraRegistration;

    private String details;

    private String natureOfLicense;
    private String licenseDuration;
    private String licensingTerritory;
    private String licenseFee;
    private String targetCustomers;
    private String royalty;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


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

    @Column(name = "istrending")
    private Boolean isTrending;

    // getters and setters
}
