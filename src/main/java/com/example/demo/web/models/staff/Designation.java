package com.example.demo.web.models.staff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "designations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer designationId;

    @Column(nullable = false, unique = true)
    private String designationName;

    private String description;
}
