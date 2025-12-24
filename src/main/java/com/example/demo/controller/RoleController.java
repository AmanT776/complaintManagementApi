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
@PreAuthorize("hasAuthority('ROLE_MANAGE')")
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponseDTO> create(@Valid @RequestBody RoleRequestDTO dto) {
        RoleResponseDTO created = roleService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return new ApiResponse<>(true,"role created successfully",created);
    }

    @GetMapping
    public ApiResponse<List<RoleResponseDTO>> getAll() {
        return new ApiResponse<>(true,"roles fetched successfully",roleService.findAll());
    }

    @Operation(summary = "Get Roles by ID", description = "Returns Role details based on ID")
    @GetMapping("/{id}")
    public ApiResponse<RoleResponseDTO> getById(@PathVariable Long id) {
        return new ApiResponse<>(true,"role fetched successsfully",roleService.findById(id));
    }

    @GetMapping("/active")
    public ApiResponse<List<RoleResponseDTO>> getActiveRoles() {
        return new ApiResponse<>(true,"active roles fetched successfully",roleService.getActiveRoles());
    }

    @GetMapping("/inactive")
    public ApiResponse<List<RoleResponseDTO>> getInactiveRoles() {
        return new ApiResponse<>(true,"inactive roles fetched successfully",roleService.getInactiveRoles());
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RoleRequestDTO dto) {
        return new ApiResponse<>(true,"role updated successfully",roleService.update(id, dto));
    }

    @PatchMapping("/{id}/activate")
    public ApiResponse<RoleResponseDTO> activateRole(@PathVariable Long id) {
        return new ApiResponse<>(true,"role updated successfully",roleService.activateRole(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ApiResponse<RoleResponseDTO> deactivateRole(@PathVariable Long id) {
        return new ApiResponse<>(true,"role deactivated successfully",roleService.deactivateRole(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}