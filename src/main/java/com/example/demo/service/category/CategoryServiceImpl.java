package com.example.demo.service.category;

import com.example.demo.dto.category.CategoryRequestDTO;
import com.example.demo.dto.category.CategoryResponseDTO;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CompliantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CompliantRepository complaintRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category not found with id: " + id));
        return categoryMapper.toDto(c);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO findByName(String name) {
        Category c = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFound("Category not found with name: " + name));
        return categoryMapper.toDto(c);
    }

    @Override
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        String clean = dto.getName().trim();
        if (categoryRepository.existsByName(clean)) {
            throw new DuplicateResourceException("Category with name '" + clean + "' already exists");
        }

        Category category = categoryMapper.toEntity(dto);
        category.setName(clean);
        category.setIsActive(true); // Default to active

        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category not found with id: " + id));

        String newName = dto.getName().trim();
        if (!existing.getName().equalsIgnoreCase(newName) && categoryRepository.existsByName(newName)) {
            throw new DuplicateResourceException("Category with name '" + newName + "' already exists");
        }

        categoryMapper.updateEntityFromDto(dto, existing);
        existing.setName(newName);

        Category saved = categoryRepository.save(existing);
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryResponseDTO activateCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category not found with id: " + id));
        category.setIsActive(true);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponseDTO deactivateCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category not found with id: " + id));
        category.setIsActive(false);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getActiveCategories() {
        return categoryMapper.toDtoList(categoryRepository.findByIsActive(true));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getInactiveCategories() {
        return categoryMapper.toDtoList(categoryRepository.findByIsActive(false));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFound("Category not found with id: " + id);
        }
        if (complaintRepository.existsByCategoryId(id)) {
            throw new IllegalStateException("Cannot delete category — complaints reference it");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}