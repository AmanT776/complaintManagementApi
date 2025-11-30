package com.BIT.BCMS.controller;

import com.BIT.BCMS.dto.DepartmentDTO;
import com.BIT.BCMS.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Department controller — DTO-based endpoints only.
 */
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> create(@Valid @RequestBody DepartmentDTO dto) {
        DepartmentDTO created = departmentService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity.ok(departmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
