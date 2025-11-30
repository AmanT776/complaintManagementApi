package com.BIT.BCMS.service;

import com.BIT.BCMS.dto.CategoryDTO;

import java.util.List;

/**
 * Service interface for Category operations.
 */
public interface CategoryService {
    List<CategoryDTO> findAll();
    CategoryDTO findById(Long id);
    CategoryDTO findByName(String name);
    CategoryDTO create(CategoryDTO dto);
    CategoryDTO update(Long id, CategoryDTO dto);
    void delete(Long id);
    boolean existsByName(String name);
}

