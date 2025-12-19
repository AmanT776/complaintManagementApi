package com.example.demo.controller;

import com.example.demo.dto.category.CategoryRequestDTO;
import com.example.demo.dto.category.CategoryResponseDTO;
import com.example.demo.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/org/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity.created(URI.create("/api/v1/org/categories/" + created.getId())).body(created);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> activateCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.activateCategory(id));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> deactivateCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deactivateCategory(id));
    }

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategoryResponseDTO>> getActiveCategories() {
        return ResponseEntity.ok(categoryService.getActiveCategories());
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<List<CategoryResponseDTO>> getInactiveCategories() {
        return ResponseEntity.ok(categoryService.getInactiveCategories());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}