package com.example.demo.web.models.staff;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staff_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;
}
