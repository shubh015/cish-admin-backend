package com.example.demo.web.models.tchnology;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "technology_person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnologyPerson {

    @EmbeddedId
    private TechnologyPersonId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("technologyId")
    @JoinColumn(name = "technology_id")
    private Technology technology;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    /**
     * Role example: "INVENTOR", "COLLABORATOR", "MAINTAINER"
     */
    @Column(name = "role", nullable = false)
    private String role;
}

