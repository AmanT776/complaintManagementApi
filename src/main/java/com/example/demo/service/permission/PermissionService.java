package com.example.demo.service.permission;

import com.example.demo.dto.permission.PermissionRequestDTO;
import com.example.demo.dto.permission.PermissionResponseDTO;
import com.example.demo.model.Permission;
import java.util.List;

public interface PermissionService {
    List<PermissionResponseDTO> getAllPermissions();
    PermissionResponseDTO getPermissionById(Long id);
    PermissionResponseDTO createPermission(PermissionRequestDTO dto);
    PermissionResponseDTO updatePermission(Long id, PermissionRequestDTO dto);
    void deletePermission(Long id);
    PermissionResponseDTO activatePermission(Long id);
    PermissionResponseDTO deactivatePermission(Long id);
    List<PermissionResponseDTO> getActivePermissions();
    List<PermissionResponseDTO> getInactivePermissions();
    Permission createPermissionIfNotFound(String name, String description, String category);
}