package com.example.demo.web.models;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "house_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityName;
    private String principalInvestigator;
    private String coPrincipalInvestigator;

        private String createdby;

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
