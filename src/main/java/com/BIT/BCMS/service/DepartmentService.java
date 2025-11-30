package com.BIT.BCMS.service;

import com.BIT.BCMS.dto.DepartmentDTO;

import java.util.List;

/**
 * Department service interface — returns DTOs.
 */
public interface DepartmentService {
    List<DepartmentDTO> findAll();
    DepartmentDTO findById(Long id);
    DepartmentDTO findByName(String name);
    DepartmentDTO create(DepartmentDTO dto);
    DepartmentDTO update(Long id, DepartmentDTO dto);
    void delete(Long id);
    boolean existsByName(String name);
}
