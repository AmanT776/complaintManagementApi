package com.example.demo.mapper;

import com.example.demo.dto.permission.PermissionRequestDTO;
import com.example.demo.dto.permission.PermissionResponseDTO;
import com.example.demo.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    PermissionResponseDTO toDto(Permission permission);
    Permission toEntity(PermissionRequestDTO dto);
    List<PermissionResponseDTO> toDtoList(List<Permission> permissions);
    // Use @MappingTarget to update the existing object
    void updateEntityFromDto(PermissionRequestDTO dto, @MappingTarget Permission entity);
}