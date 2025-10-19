package com.example.demo.web.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "staff_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "subDepartment") // 👈 prevent recursion in toString()
public class StaffMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String designation;

    @Column(unique = true)
    private String icarEmail;

    private String alternateEmail;
    private String specialization;
    private LocalDate joiningDate;

    @Column(columnDefinition = "TEXT")
    private String mscFrom;

    @Column(columnDefinition = "TEXT")
    private String phdFrom;

    private String photo;
    private Boolean isHead = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_department_id")
    @JsonBackReference // ✅ Jackson will not serialize this side
    private SubDepartment subDepartment;
}
