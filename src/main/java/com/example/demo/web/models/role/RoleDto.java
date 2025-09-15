package com.example.demo.web.models.role;


import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private String description;

    public static RoleDto fromEntity(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());  
        return dto;
    }
}