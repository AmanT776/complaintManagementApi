package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.category.CategoryRequestDTO;
import com.example.demo.dto.category.CategoryResponseDTO;
import com.example.demo.model.Category;
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
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity.created(URI.create("/api/v1/org/categories/" + created.getId())).body(created);
    }

    @Operation(summary = "Get all categories", description = "Returns all categories")
    @GetMapping
    public ApiResponse<List<CategoryResponseDTO>> getAll() {
        List<CategoryResponseDTO> categories = categoryService.findAll();
        return new ApiResponse<>(true, "Categories fetched successfully", categories);
    }

    @Operation(summary = "Get category by ID", description = "Returns category details based on ID")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Operation(summary = "Update category", description = "Updates an existing category")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @Operation(summary = "Activate category", description = "Activates a category by ID")
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> activateCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.activateCategory(id));
    }

    @Operation(summary = "Deactivate category", description = "Deactivates a category by ID")
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<CategoryResponseDTO> deactivateCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deactivateCategory(id));
    }

    @Operation(summary = "Get active categories", description = "Returns all active categories")
    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategoryResponseDTO>> getActiveCategories() {
        return ResponseEntity.ok(categoryService.getActiveCategories());
    }

    @Operation(summary = "Get inactive categories", description = "Returns all inactive categories")
    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<List<CategoryResponseDTO>> getInactiveCategories() {
        return ResponseEntity.ok(categoryService.getInactiveCategories());
    }

    @Operation(summary = "Delete category", description = "Deletes a category by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}