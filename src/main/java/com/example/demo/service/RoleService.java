package com.example.demo.service;



import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.models.role.Role;
import com.example.demo.web.models.role.RoleDto;
import com.example.demo.web.models.role.RoleRequest;


import com.example.demo.web.models.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;

    public List<RoleDto> getAllRoles() {
        return roleRepo.findAll().stream().map(RoleDto::fromEntity).toList();
    }

    public RoleDto createRole(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        return RoleDto.fromEntity(roleRepo.save(role));
    }

    public RoleDto updateRole(Long id, RoleRequest request) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        return RoleDto.fromEntity(roleRepo.save(role));
    }

    public void deleteRole(Long id) {
        if (!roleRepo.existsById(id)) {
            throw new EntityNotFoundException("Role not found");
        }
        roleRepo.deleteById(id);
    }

    public void assignRoleToUser(Long userId, Long roleId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        user.getRoles().add(role);
        userRepo.save(user);
    }

    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        user.getRoles().remove(role);
        userRepo.save(user);
    }
}
