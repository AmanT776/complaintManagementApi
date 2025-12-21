package com.example.demo.controller;

import com.example.demo.dto.organizationalUnit.OrganizationalUnitRequestDTO;
import com.example.demo.dto.organizationalUnit.OrganizationalUnitResponseDTO;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Organizational Units", description = "Organizational unit management APIs")
@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
public class OrganizationalUnitController {

    private final OrganizationalUnitService unitService;


    @Operation(summary = "Create organizational unit", description = "Creates a new organizational unit")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationalUnitResponseDTO> createUnit(@Valid @RequestBody OrganizationalUnitRequestDTO requestDTO) {
        return new ResponseEntity<>(unitService.createUnit(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all organizational units", description = "Returns all organizational units")
    @GetMapping
    public ResponseEntity<List<OrganizationalUnitResponseDTO>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    @Operation(summary = "Get organizational unit by ID", description = "Returns organizational unit details based on ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationalUnitResponseDTO> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @Operation(summary = "Get units by type", description = "Returns all organizational units of a specific type")
    // e.g., GET /api/v1/units/type/1 (Returns all Faculties)
    @GetMapping("/type/{typeName}")
    public ResponseEntity<List<OrganizationalUnitResponseDTO>> getUnitsByType(@PathVariable String typeName) {
        return ResponseEntity.ok(unitService.getUnitsByType(typeName));
    }

    @Operation(summary = "Get units by parent", description = "Returns all organizational units under a specific parent unit")
    // e.g., GET /api/v1/units/parent/5 (Returns all Depts in Faculty #5)
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<OrganizationalUnitResponseDTO>> getUnitsByParent(@PathVariable Long parentId) {
        return ResponseEntity.ok(unitService.getUnitsByParent(parentId));
    }

    @Operation(summary = "Update organizational unit", description = "Updates an existing organizational unit")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationalUnitResponseDTO> updateUnit(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationalUnitRequestDTO requestDTO) {
        return ResponseEntity.ok(unitService.updateUnit(id, requestDTO));
    }


    @Operation(summary = "Delete organizational unit", description = "Deletes an organizational unit by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.noContent().build();
    }
}