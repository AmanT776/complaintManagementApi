package com.BIT.BCMS.service;

import com.BIT.BCMS.entities.Category;
import com.BIT.BCMS.dto.CategoryDTO;
import com.BIT.BCMS.exceptions.DuplicateResourceException;
import com.BIT.BCMS.exceptions.ResourceNotFoundException;
import com.BIT.BCMS.mapper.CategoryMapper;
import com.BIT.BCMS.repository.CategoryRepository;
import com.BIT.BCMS.repository.ComplaintRepository;
import com.BIT.BCMS.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CategoryServiceImpl uses CategoryMapper for conversions.
 * Prevents deletion when complaints reference the category.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ComplaintRepository complaintRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDto(c);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findByName(String name) {
        Category c = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name));
        return categoryMapper.toDto(c);
    }

    @Override
    @Transactional
    public CategoryDTO create(CategoryDTO dto) {
        String clean = dto.getName().trim();
        if (categoryRepository.existsByName(clean)) {
            throw new DuplicateResourceException("Category with name '" + clean + "' already exists");
        }
        dto.setName(clean);
        Category saved = categoryRepository.save(categoryMapper.toEntity(dto));
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        String newName = dto.getName().trim();
        if (!existing.getName().equalsIgnoreCase(newName) && categoryRepository.existsByName(newName)) {
            throw new DuplicateResourceException("Category with name '" + newName + "' already exists");
        }

        dto.setName(newName);
        categoryMapper.updateEntityFromDto(dto, existing);
        Category saved = categoryRepository.save(existing);
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        if (complaintRepository.existsByCategoryId(id)) {
            throw new IllegalStateException("Cannot delete category with id " + id + " — complaints reference it");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
