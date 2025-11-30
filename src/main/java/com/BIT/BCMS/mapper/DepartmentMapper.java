package com.BIT.BCMS.mapper;

import com.BIT.BCMS.entities.Department;
import com.BIT.BCMS.dto.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct mapper for Department entity.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDTO toDto(Department entity);
    Department toEntity(DepartmentDTO dto);
    void updateEntityFromDto(DepartmentDTO dto, @MappingTarget Department entity);
    List<DepartmentDTO> toDtoList(List<Department> entities);
}

