package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeRequestDTO;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeResponseDTO;
import com.example.demo.service.organizationalUnitTypeService.OrganizationalUnitTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Organizational Unit Types", description = "Organizational unit type management APIs")
@RestController
@RequestMapping("/api/v1/unit-types")
@RequiredArgsConstructor
public class OrganizationalUnitTypeController {

    private final OrganizationalUnitTypeService unitTypeService;

    @Operation(summary = "Create organizational unit type", description = "Creates a new organizational unit type")
    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationalUnitTypeResponseDTO>> createType(
            @Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        OrganizationalUnitTypeResponseDTO created = unitTypeService.createOrganizationalUnitType(requestDto);
        return new ResponseEntity<>(new ApiResponse<>(true, "Unit Type created successfully", created), HttpStatus.CREATED);
    }

    @Operation(summary = "Get organizational unit type by ID", description = "Returns organizational unit type details based on ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationalUnitTypeResponseDTO>> getTypeById(@PathVariable Long id) {
        OrganizationalUnitTypeResponseDTO type = unitTypeService.findByIdDto(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found"));
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit Type retrieved successfully", type));
    }

    @Operation(summary = "Get all organizational unit types", description = "Returns all organizational unit types")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationalUnitTypeResponseDTO>>> getAllTypes() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit Types retrieved successfully", unitTypeService.findAllDto()));
    }

    @Operation(summary = "Update organizational unit type", description = "Updates an existing organizational unit type")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationalUnitTypeResponseDTO>> updateType(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        OrganizationalUnitTypeResponseDTO updated = unitTypeService.updateOrganizationalUnitType(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit Type updated successfully", updated));
    }

    @Operation(summary = "Delete organizational unit type", description = "Deletes an organizational unit type by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteType(@PathVariable Long id) {
        unitTypeService.deleteOrganizationalUnitType(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit Type deleted successfully", null));
    }
}