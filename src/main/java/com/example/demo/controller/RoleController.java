package com.example.demo.controller;

import com.example.demo.dto.role.RoleRequestDTO;
import com.example.demo.dto.role.RoleResponseDTO;
import com.example.demo.service.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_MANAGE')")
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDTO> create(@Valid @RequestBody RoleRequestDTO dto) {
        RoleResponseDTO created = roleService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<RoleResponseDTO>> getActiveRoles() {
        return ResponseEntity.ok(roleService.getActiveRoles());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<RoleResponseDTO>> getInactiveRoles() {
        return ResponseEntity.ok(roleService.getInactiveRoles());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RoleRequestDTO dto) {
        return ResponseEntity.ok(roleService.update(id, dto));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<RoleResponseDTO> activateRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.activateRole(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<RoleResponseDTO> deactivateRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.deactivateRole(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}