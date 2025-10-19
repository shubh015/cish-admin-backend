package com.example.demo.web.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sub_departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employees") // 👈 prevent infinite recursion in toString()
public class SubDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subDeptId;
    private String subDeptName;

    @OneToMany(mappedBy = "subDepartment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // ✅ Jackson will serialize this side
    private List<StaffMember> employees;
}
