package com.BIT.BCMS.mapper;

import com.BIT.BCMS.entities.Role;
import com.BIT.BCMS.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct mapper for Role <-> RoleDTO.
 * componentModel = "spring" allows Spring to inject the mapper.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDto(Role entity);
    Role toEntity(RoleDTO dto);

    // update existing entity from dto (used in update operations)
    void updateEntityFromDto(RoleDTO dto, @MappingTarget Role entity);

    // convenience for lists
    List<RoleDTO> toDtoList(List<Role> entities);
}

