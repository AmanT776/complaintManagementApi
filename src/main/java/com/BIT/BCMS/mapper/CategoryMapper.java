package com.BIT.BCMS.mapper;

import com.BIT.BCMS.entities.Category;
import com.BIT.BCMS.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct mapper for Category.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category entity);
    Category toEntity(CategoryDTO dto);
    void updateEntityFromDto(CategoryDTO dto, @MappingTarget Category entity);
    List<CategoryDTO> toDtoList(List<Category> entities);
}

