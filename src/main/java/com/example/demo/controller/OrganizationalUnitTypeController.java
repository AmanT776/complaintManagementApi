package com.example.demo.controller;


import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeRequestDTO;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeResponseDTO;
import com.example.demo.model.OrganizationalUnitType;
import com.example.demo.service.organizationalUnitTypeService.OrganizationalUnitTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Organizational Unit Types", description = "Organizational unit type management APIs")
@RestController
@RequestMapping("/api/v1/unit-types")
@RequiredArgsConstructor
public class OrganizationalUnitTypeController {

    private final OrganizationalUnitTypeService unitTypeService;

    @Operation(summary = "Create organizational unit type", description = "Creates a new organizational unit type")
    @PostMapping
    public ApiResponse<OrganizationalUnitTypeResponseDTO> createType(@Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        return new ApiResponse<>(true,"organizational unit type created successfully",unitTypeService.createOrganizationalUnitType(requestDto));
    }

    @Operation(summary = "Get organizational unit type by ID", description = "Returns organizational unit type details based on ID")
    @GetMapping("/{id}")
    public ApiResponse<ApiResponse<Optional<OrganizationalUnitType>>> getTypeById(@PathVariable Long id) {
        ApiResponse<Optional<OrganizationalUnitType>> unit = unitTypeService.findByIdDto(id);
        return new ApiResponse<>(true,"organizational unit type fetched successfully",unit);
    }

    @Operation(summary = "Get all organizational unit types", description = "Returns all organizational unit types")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationalUnitTypeResponseDTO>>> getAllTypes() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Unit Types retrieved successfully", unitTypeService.findAllDto()));
    }

    @Operation(summary = "Update organizational unit type", description = "Updates an existing organizational unit type")
    @PutMapping("/{id}")
    public ApiResponse<OrganizationalUnitTypeResponseDTO> updateType(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        return new ApiResponse<>(true,"unit Type updated successfully",unitTypeService.updateOrganizationalUnitType(id, requestDto));
    }

    @Operation(summary = "Delete organizational unit type", description = "Deletes an organizational unit type by ID")
    @DeleteMapping("/{id}")
    public ApiResponse<ResponseEntity<Object>> deleteType(@PathVariable Long id) {
        unitTypeService.deleteOrganizationalUnitType(id);
        return new ApiResponse<>(true,"organizational unit type deleted successfully",ResponseEntity.noContent().build());
    }
}