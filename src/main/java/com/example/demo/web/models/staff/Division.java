    package com.example.demo.web.models.staff;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "divisions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer divisionId;

    @Column(nullable = false, unique = true)
    private String divisionName;

    private String location;
}
