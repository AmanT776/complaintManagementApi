package com.example.demo.mapper;

import com.example.demo.dto.role.RoleRequestDTO;
import com.example.demo.dto.role.RoleResponseDTO;
import com.example.demo.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleResponseDTO toDto(Role role);
    Role toEntity(RoleRequestDTO dto);

    // update existing entity from dto (used in update operations)
    void updateEntityFromDto(RoleRequestDTO dto, @MappingTarget Role entity);

    // convenience for lists
    List<RoleResponseDTO> toDtoList(List<Role> roles);
}

