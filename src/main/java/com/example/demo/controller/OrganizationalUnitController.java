package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<OrganizationalUnitResponseDTO>> createUnit(@Valid @RequestBody OrganizationalUnitRequestDTO requestDTO) {
        OrganizationalUnitResponseDTO created = unitService.createUnit(requestDTO);
        return new ResponseEntity<>(new ApiResponse<>(true, "Organizational Unit created successfully", created), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all organizational units", description = "Returns all organizational units")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationalUnitResponseDTO>>> getAllUnits() {
        return ResponseEntity.ok(new ApiResponse<>(true, "All Units retrieved successfully", unitService.getAllUnits()));
    }

    @Operation(summary = "Get organizational unit by ID", description = "Returns organizational unit details based on ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationalUnitResponseDTO>> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit retrieved successfully", unitService.getUnitById(id)));
    }

    @Operation(summary = "Get units by type", description = "Returns all organizational units of a specific type")
    @GetMapping("/type/{typeName}")
    public ResponseEntity<ApiResponse<List<OrganizationalUnitResponseDTO>>> getUnitsByType(@PathVariable String typeName) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Units retrieved successfully", unitService.getUnitsByType(typeName)));
    }

    @Operation(summary = "Get units by parent", description = "Returns all organizational units under a specific parent unit")
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<ApiResponse<List<OrganizationalUnitResponseDTO>>> getUnitsByParent(@PathVariable Long parentId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Child units retrieved successfully", unitService.getUnitsByParent(parentId)));
    }

    @Operation(summary = "Update organizational unit", description = "Updates an existing organizational unit")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<OrganizationalUnitResponseDTO>> updateUnit(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationalUnitRequestDTO requestDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit updated successfully", unitService.updateUnit(id, requestDTO)));
    }

    @Operation(summary = "Delete organizational unit", description = "Deletes an organizational unit by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit deleted successfully", null));
    }
}