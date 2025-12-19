package com.example.demo.service.role;

import com.example.demo.dto.role.RoleRequestDTO;
import com.example.demo.dto.role.RoleResponseDTO;

import java.util.List;

/**
 * Service interface for Role operations.
 */
public interface RoleService {
    List<RoleResponseDTO> findAll();
    RoleResponseDTO findById(Long id);
    RoleResponseDTO findByName(String name);
    RoleResponseDTO create(RoleRequestDTO dto);
    RoleResponseDTO update(Long id, RoleRequestDTO dto);
    void delete(Long id);
    boolean existsByName(String name);
    RoleResponseDTO activateRole(Long id);
    RoleResponseDTO deactivateRole(Long id);
    List<RoleResponseDTO> getActiveRoles();
    List<RoleResponseDTO> getInactiveRoles();
    RoleResponseDTO create(String name, String description);
}