package com.example.demo.mapper;

import com.example.demo.dto.permission.PermissionDTO;
import com.example.demo.model.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDTO toDto(Permission permission);
    Permission toEntity(PermissionDTO dto);
    List<PermissionDTO> toDtoList(List<Permission> permissions);
}