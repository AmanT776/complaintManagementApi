package com.example.demo.controller;


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
    public ResponseEntity<OrganizationalUnitTypeResponseDTO> createType(
            @Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        return new ResponseEntity<>(unitTypeService.createOrganizationalUnitType(requestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get organizational unit type by ID", description = "Returns organizational unit type details based on ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationalUnitTypeResponseDTO> getTypeById(@PathVariable Long id) {
        return unitTypeService.findByIdDto(id)
                .map(type -> new ResponseEntity<>(type, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found"));
    }

    @Operation(summary = "Get all organizational unit types", description = "Returns all organizational unit types")
    @GetMapping
    public List<OrganizationalUnitTypeResponseDTO> getAllTypes() {
        return unitTypeService.findAllDto();
    }

    @Operation(summary = "Update organizational unit type", description = "Updates an existing organizational unit type")
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationalUnitTypeResponseDTO> updateType(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        return new ResponseEntity<>(unitTypeService.updateOrganizationalUnitType(id, requestDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete organizational unit type", description = "Deletes an organizational unit type by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        unitTypeService.deleteOrganizationalUnitType(id);
        return ResponseEntity.noContent().build();
    }
}
