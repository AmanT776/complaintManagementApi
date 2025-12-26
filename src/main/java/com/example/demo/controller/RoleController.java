package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.role.RoleRequestDTO;
import com.example.demo.dto.role.RoleResponseDTO;
import com.example.demo.service.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Roles", description = "Role management APIs")
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create a new role", description = "Creates a new role with the provided details")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> create(@Valid @RequestBody RoleRequestDTO dto) {
        RoleResponseDTO created = roleService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse<>(true, "Role created successfully", created));
    }

    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Roles retrieved successfully", roleService.findAll()));
    }

    @Operation(summary = "Get role by ID", description = "Returns role details based on ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Role retrieved successfully", roleService.findById(id)));
    }

    @Operation(summary = "Get active roles", description = "Retrieves a list of all active roles")
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getActiveRoles() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Active roles retrieved successfully", roleService.getActiveRoles()));
    }

    @Operation(summary = "Get inactive roles", description = "Retrieves a list of all inactive roles")
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getInactiveRoles() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Inactive roles retrieved successfully", roleService.getInactiveRoles()));
    }

    @Operation(summary = "Update role", description = "Updates an existing role by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody RoleRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Role updated successfully", roleService.update(id, dto)));
    }

    @Operation(summary = "Activate role", description = "Activates a specific role by ID")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> activateRole(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Role activated successfully", roleService.activateRole(id)));
    }

    @Operation(summary = "Deactivate role", description = "Deactivates a specific role by ID")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> deactivateRole(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Role deactivated successfully", roleService.deactivateRole(id)));
    }

    @Operation(summary = "Delete role", description = "Deletes a role by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Role deleted successfully", null));
    }
}