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
    public ApiResponse<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return new ApiResponse(true,"category created successfully",created);
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
    public ApiResponse<CategoryResponseDTO> getById(@PathVariable Long id) {
        return new ApiResponse<>(true,"category fetched successfully",categoryService.findById(id));
    }

    @Operation(summary = "Update category", description = "Updates an existing category")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ApiResponse<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
        return new ApiResponse(true,"category updated successfully",categoryService.update(id, dto));
    }

    @Operation(summary = "Activate category", description = "Activates a category by ID")
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ApiResponse<CategoryResponseDTO> activateCategory(@PathVariable Long id) {
        return new ApiResponse<>(true,"category activated successfully",categoryService.activateCategory(id));
    }

    @Operation(summary = "Deactivate category", description = "Deactivates a category by ID")
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ApiResponse<CategoryResponseDTO> deactivateCategory(@PathVariable Long id) {
        return new ApiResponse<>(true,"category deactivated successfully",categoryService.deactivateCategory(id));
    }

    @Operation(summary = "Get active categories", description = "Returns all active categories")
    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<List<CategoryResponseDTO>> getActiveCategories() {
        return new ApiResponse<>(true,"active categories fetched successfully",categoryService.getActiveCategories());
    }

    @Operation(summary = "Get inactive categories", description = "Returns all inactive categories")
    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ApiResponse<List<CategoryResponseDTO>> getInactiveCategories() {
        return new ApiResponse<>(true,"inactive categories fetched successfully",categoryService.getInactiveCategories());
    }

    @Operation(summary = "Delete category", description = "Deletes a category by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_MANAGE')")
    public ApiResponse<ResponseEntity<Object>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return new ApiResponse<>(true,"category deleted successfully",ResponseEntity.noContent().build());
    }
}