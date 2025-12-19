package com.example.demo.service.organizationalUnit;

import com.example.demo.dto.organizationalUnit.OrganizationalUnitRequestDTO;
import com.example.demo.dto.organizationalUnit.OrganizationalUnitResponseDTO;
import java.util.List;

/**
 * Service interface for Organizational Unit operations.
 */
public interface OrganizationalUnitService {

    OrganizationalUnitResponseDTO createUnit(OrganizationalUnitRequestDTO requestDTO);

    List<OrganizationalUnitResponseDTO> getAllUnits();

    OrganizationalUnitResponseDTO getUnitById(Long id);

    List<OrganizationalUnitResponseDTO> getUnitsByType(Long typeId);

    List<OrganizationalUnitResponseDTO> getUnitsByParent(Long parentId);

    OrganizationalUnitResponseDTO updateUnit(Long id, OrganizationalUnitRequestDTO requestDTO);

    void deleteUnit(Long id);
}