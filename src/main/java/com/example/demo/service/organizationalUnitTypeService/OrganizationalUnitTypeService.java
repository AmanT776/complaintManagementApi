package com.example.demo.service.organizationalUnitTypeService;


import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeRequestDTO;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeResponseDTO;
import com.example.demo.model.OrganizationalUnitType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

    public interface OrganizationalUnitTypeService {

        // Entity methods (for internal use)
        OrganizationalUnitType createOrganizationalUnitType(String name);
        OrganizationalUnitType findById(Long id);
        Optional<OrganizationalUnitType> findByName(String name);
        List<OrganizationalUnitType> findAll();
        OrganizationalUnitType updateOrganizationalUnitType(Long id, String name);
        void deleteOrganizationalUnitType(Long id);
        boolean existsByName(String name);

    // DTO methods (API use)
    OrganizationalUnitTypeResponseDTO createOrganizationalUnitType(OrganizationalUnitTypeRequestDTO dto);
    ApiResponse<Optional<OrganizationalUnitType>> findByIdDto(Long id);
    List<OrganizationalUnitTypeResponseDTO> findAllDto();
    OrganizationalUnitTypeResponseDTO updateOrganizationalUnitType(Long id, OrganizationalUnitTypeRequestDTO dto);
}

