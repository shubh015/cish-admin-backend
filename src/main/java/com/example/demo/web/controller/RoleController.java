package com.example.demo.web.controller;



import com.example.demo.service.RoleService;
import com.example.demo.web.models.role.RoleDto;
import com.example.demo.web.models.role.RoleRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign/{userId}/{roleId}")
    public ResponseEntity<Void> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        roleService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/{userId}/{roleId}")
    public ResponseEntity<Void> removeRole(@PathVariable Long userId, @PathVariable Long roleId) {
        roleService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok().build();
    }
}
