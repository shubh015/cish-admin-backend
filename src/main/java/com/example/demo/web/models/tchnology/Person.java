package com.example.demo.web.models.tchnology;



import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "role_hint")
    private String roleHint;

    private String email;
    private String affiliation;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
