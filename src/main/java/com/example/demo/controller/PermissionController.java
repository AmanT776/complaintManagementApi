package com.example.demo.controller;

import com.example.demo.dto.permission.PermissionRequestDTO;
import com.example.demo.dto.permission.PermissionResponseDTO;
import com.example.demo.service.permission.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGE')")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionResponseDTO> createPermission(@Valid @RequestBody PermissionRequestDTO dto) {
        PermissionResponseDTO created = permissionService.createPermission(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<PermissionResponseDTO>> getActivePermissions() {
        return ResponseEntity.ok(permissionService.getActivePermissions());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<PermissionResponseDTO>> getInactivePermissions() {
        return ResponseEntity.ok(permissionService.getInactivePermissions());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionRequestDTO dto) {
        return ResponseEntity.ok(permissionService.updatePermission(id, dto));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<PermissionResponseDTO> activatePermission(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.activatePermission(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PermissionResponseDTO> deactivatePermission(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.deactivatePermission(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}