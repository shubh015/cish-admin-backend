package com.example.demo.web.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subDeptId;
    private String subDeptName;
    private String division;

    private String name;
    private String designation;
    private String icarEmail;
    private String alternateEmail;
    private String specialization;
    private LocalDate joiningDate;
    private String mscFrom;
    private String phdFrom;

    private String createdby;

    @Column(length = 1000)
    private String photo;

    private Boolean isHead = false;
    private Boolean isDirector = false;

    @Column(length = 1000)
    private String descriptionDirector;

    @Builder.Default
    @Column(name = "ispublished", nullable = false)
    private Boolean isPublished = false;

    @Builder.Default
    @Column(name = "isactive", nullable = false)
    private Boolean isActive = true;

    @Builder.Default
    @Column(name = "backtocreator", nullable = false)
    private Boolean backToCreator = false;

}
