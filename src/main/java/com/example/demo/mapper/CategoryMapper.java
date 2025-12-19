package com.example.demo.mapper;

import com.example.demo.dto.category.CategoryRequestDTO;
import com.example.demo.dto.category.CategoryResponseDTO;
import com.example.demo.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * MapStruct mapper for Category.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryResponseDTO toDto(Category entity);
    Category toEntity(CategoryRequestDTO dto);
    void updateEntityFromDto(CategoryRequestDTO dto, @MappingTarget Category entity);
    List<CategoryResponseDTO> toDtoList(List<Category> entities);
}

