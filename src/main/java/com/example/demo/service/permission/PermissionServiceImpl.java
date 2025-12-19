package com.example.demo.service.permission;

import com.example.demo.dto.permission.PermissionRequestDTO;
import com.example.demo.dto.permission.PermissionResponseDTO;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.model.Permission;
import com.example.demo.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponseDTO> getAllPermissions() {
        return permissionMapper.toDtoList(permissionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponseDTO getPermissionById(Long id) {
        Permission p = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Permission not found with ID: " + id));
        return permissionMapper.toDto(p);
    }

    @Override
    @Transactional
    public PermissionResponseDTO createPermission(PermissionRequestDTO dto) {
        String name = dto.getName().trim().toUpperCase();

        if (permissionRepository.existsByName(name)) {
            throw new IllegalArgumentException("Permission '" + name + "' already exists");
        }

        Permission perm = permissionMapper.toEntity(dto);
        perm.setName(name);
        perm.setIsActive(true);

        return permissionMapper.toDto(permissionRepository.save(perm));
    }

    @Override
    @Transactional
    public PermissionResponseDTO updatePermission(Long id, PermissionRequestDTO dto) {
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Permission not found with ID: " + id));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            String newName = dto.getName().trim().toUpperCase();
            if (!existing.getName().equals(newName) && permissionRepository.existsByName(newName)) {
                throw new IllegalArgumentException("Permission Name '" + newName + "' is already taken.");
            }
            existing.setName(newName);
        }

        permissionMapper.updateEntityFromDto(dto, existing);

        return permissionMapper.toDto(permissionRepository.save(existing));
    }


    @Override
    @Transactional
    public void deletePermission(Long id) {
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Permission not found with ID: " + id));

        if (existing.getRoles() != null && !existing.getRoles().isEmpty()) {
            throw new IllegalStateException("Permission is assigned to roles and cannot be deleted.");
        }

        permissionRepository.delete(existing);
    }

    @Override
    @Transactional
    public Permission createPermissionIfNotFound(String name, String description, String category) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> permissionRepository.save(Permission.builder()
                        .name(name)
                        .description(description)
                        .category(category)
                        .isActive(true)
                        .build()));
    }

    @Override
    @Transactional
    public PermissionResponseDTO activatePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Permission not found with id: " + id));
        permission.setIsActive(true);
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Override
    @Transactional
    public PermissionResponseDTO deactivatePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Permission not found with id: " + id));
        permission.setIsActive(false);
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponseDTO> getActivePermissions() {
        return permissionMapper.toDtoList(permissionRepository.findByIsActive(true));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponseDTO> getInactivePermissions() {
        return permissionMapper.toDtoList(permissionRepository.findByIsActive(false));
    }
}