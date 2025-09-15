 package com.example.demo.web.models.tchnology;



import jakarta.persistence.*;

@Entity
@Table(name = "tech_ip")
public class TechIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

    private String ipType;     // PATENT | DESIGN | PPVFRA
    private String regNumber;
    private String status;
    private String remarks;

    // getters/setters...
}
