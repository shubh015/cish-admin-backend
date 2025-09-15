package com.example.demo.web.models.tchnology;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "technology")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String slug;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(name = "short_description", length = 2000)
    private String shortDescription;

    @Column(name = "tech_details", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> techDetails;

    @Column(name = "ic_no", length = 64)
    private String icNo;

    @Column(name = "ppvfra_reg", length = 64)
    private String ppvfraReg;

    @Column(name = "ip_protection")
    private String ipProtection;

    @Column(name = "year_development")
    private Integer yearDevelopment;

    @Column(name = "year_release")
    private Integer yearRelease;

    @Column(name = "year_commercialization")
    private Integer yearCommercialization;

    @Column(name = "licensing_summary")
    private String licensingSummary;

    @Column(name = "target_customers", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> targetCustomers;

    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @OneToMany(mappedBy = "technology", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LicensingTerm> licensingTerms;

    @OneToMany(mappedBy = "technology", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TechnologyPerson> people;
}
