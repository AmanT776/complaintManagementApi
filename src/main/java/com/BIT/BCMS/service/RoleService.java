package com.BIT.BCMS.service;

import com.BIT.BCMS.dto.RoleDTO;

import java.util.List;

/**
 * Service interface for Role operations.
 * All methods use DTOs to keep controllers decoupled from persistence entities.
 */
public interface RoleService {
    List<RoleDTO> findAll();
    RoleDTO findById(Long id);
    RoleDTO findByName(String name);
    RoleDTO create(RoleDTO dto);
    RoleDTO update(Long id, RoleDTO dto);
    void delete(Long id);
    boolean existsByName(String name);
}
