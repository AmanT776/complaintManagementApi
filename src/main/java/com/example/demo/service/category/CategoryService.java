package com.example.demo.service.category;

import com.example.demo.dto.category.CategoryRequestDTO;
import com.example.demo.dto.category.CategoryResponseDTO;

import java.util.List;

/**
 * Service interface for Category operations.
 */
public interface CategoryService {
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO findByName(String name);
//    CategoryResponseDTO getById( Long id);
    CategoryResponseDTO create(CategoryRequestDTO dto);
    CategoryResponseDTO update(Long id, CategoryRequestDTO dto);
    void delete(Long id);
    boolean existsByName(String name);
    CategoryResponseDTO activateCategory(Long id);

    CategoryResponseDTO deactivateCategory(Long id);

    List<CategoryResponseDTO> getActiveCategories();

    List<CategoryResponseDTO> getInactiveCategories();
}

