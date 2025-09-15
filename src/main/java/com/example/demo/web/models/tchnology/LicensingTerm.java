package com.example.demo.web.models.tchnology;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "licensing_term")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicensingTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

    @Column(name = "nature_of_license")
    private String natureOfLicense;

    @Column(name = "duration_years")
    private Integer durationYears;

    private String territory;

    @Column(name = "license_fee_cents")
    private Long licenseFeeCents;
@Column(name = "rebate_percent", precision = 5, scale = 2) 
private BigDecimal rebatePercent;

    private String royalty;
    private String notes;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
