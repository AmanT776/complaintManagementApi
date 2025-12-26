package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.category.CategoryRequestDTO;
import com.example.demo.dto.category.CategoryResponseDTO;
import com.example.demo.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Categories", description = "Category management APIs")
@RestController
@RequestMapping("/api/v1/org/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create category", description = "Creates a new category")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/org/categories/" + created.getId()))
                .body(new ApiResponse<>(true, "Category created successfully", created));
    }

    @Operation(summary = "Get all categories", description = "Returns all categories")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Categories retrieved successfully", categoryService.findAll()));
    }

    @Operation(summary = "Get category by ID", description = "Returns category details based on ID")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Category retrieved successfully", categoryService.findById(id)));
    }

    @Operation(summary = "Update category", description = "Updates an existing category")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Category updated successfully", categoryService.update(id, dto)));
    }

    @Operation(summary = "Activate category", description = "Activates a category by ID")
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> activateCategory(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Category activated successfully", categoryService.activateCategory(id)));
    }

    @Operation(summary = "Deactivate category", description = "Deactivates a category by ID")
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> deactivateCategory(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Category deactivated successfully", categoryService.deactivateCategory(id)));
    }

    @Operation(summary = "Get active categories", description = "Returns all active categories")
    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getActiveCategories() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Active categories retrieved successfully", categoryService.getActiveCategories()));
    }

    @Operation(summary = "Get inactive categories", description = "Returns all inactive categories")
    @GetMapping("/inactive")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getInactiveCategories() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Inactive categories retrieved successfully", categoryService.getInactiveCategories()));
    }

    @Operation(summary = "Delete category", description = "Deletes a category by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category deleted successfully", null));
    }
}