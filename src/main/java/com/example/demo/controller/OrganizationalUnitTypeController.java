package com.example.demo.controller;


import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeRequestDTO;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeResponseDTO;
import com.example.demo.service.organizationalUnitTypeService.OrganizationalUnitTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/unit-types")
@RequiredArgsConstructor
public class OrganizationalUnitTypeController {

    private final OrganizationalUnitTypeService unitTypeService;

    // CREATE
    @PostMapping
    public ResponseEntity<OrganizationalUnitTypeResponseDTO> createType(
            @Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        return new ResponseEntity<>(unitTypeService.createOrganizationalUnitType(requestDto), HttpStatus.CREATED);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationalUnitTypeResponseDTO> getTypeById(@PathVariable Long id) {
        return unitTypeService.findByIdDto(id)
                .map(type -> new ResponseEntity<>(type, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found"));
    }

    // GET ALL
    @GetMapping
    public List<OrganizationalUnitTypeResponseDTO> getAllTypes() {
        return unitTypeService.findAllDto();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationalUnitTypeResponseDTO> updateType(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationalUnitTypeRequestDTO requestDto) {
        return new ResponseEntity<>(unitTypeService.updateOrganizationalUnitType(id, requestDto), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        unitTypeService.deleteOrganizationalUnitType(id);
        return ResponseEntity.noContent().build();
    }
}
