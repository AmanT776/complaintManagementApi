package com.example.demo.controller;

import com.example.demo.dto.permission.PermissionRequestDTO;
import com.example.demo.dto.permission.PermissionResponseDTO;
import com.example.demo.service.permission.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Permissions", description = "Permission management APIs")
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGE')")
public class PermissionController {

    private final PermissionService permissionService;

    @Operation(summary = "Create permission", description = "Creates a new permission")
    @PostMapping
    public ResponseEntity<PermissionResponseDTO> createPermission(@Valid @RequestBody PermissionRequestDTO dto) {
        PermissionResponseDTO created = permissionService.createPermission(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all permissions", description = "Returns all permissions")
    @GetMapping
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }
    @Operation(summary = "Get permission by ID", description = "Returns permission details based on ID")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }
    @Operation(summary = "Get active permissions", description = "Returns all active permissions")
    @GetMapping("/active")
    public ResponseEntity<List<PermissionResponseDTO>> getActivePermissions() {
        return ResponseEntity.ok(permissionService.getActivePermissions());
    }

    @Operation(summary = "Get inactive permissions", description = "Returns all inactive permissions")
    @GetMapping("/inactive")
    public ResponseEntity<List<PermissionResponseDTO>> getInactivePermissions() {
        return ResponseEntity.ok(permissionService.getInactivePermissions());
    }

    @Operation(summary = "Update permission", description = "Updates an existing permission")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionRequestDTO dto) {
        return ResponseEntity.ok(permissionService.updatePermission(id, dto));
    }

    @Operation(summary = "Activate permission", description = "Activates a permission by ID")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<PermissionResponseDTO> activatePermission(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.activatePermission(id));
    }

    @Operation(summary = "Deactivate permission", description = "Deactivates a permission by ID")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PermissionResponseDTO> deactivatePermission(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.deactivatePermission(id));
    }

    @Operation(summary = "Delete permission", description = "Deletes a permission by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}