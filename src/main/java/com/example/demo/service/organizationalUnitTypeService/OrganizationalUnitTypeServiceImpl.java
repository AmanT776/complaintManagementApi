package com.example.demo.service.organizationalUnitTypeService;


import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeRequestDTO;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeResponseDTO;
import com.example.demo.mapper.OrganizationalUnitTypeMapper;
import com.example.demo.model.OrganizationalUnitType;
import com.example.demo.repository.OrganizationalUnitTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationalUnitTypeServiceImpl implements OrganizationalUnitTypeService {

    private final OrganizationalUnitTypeRepository organizationalUnitTypeRepository;
    private final OrganizationalUnitTypeMapper organizationalUnitTypeMapper;

    // Entity methods
    @Override
    public OrganizationalUnitType createOrganizationalUnitType(String name) {
        if (existsByName(name)) {
            throw new RuntimeException("Organizational unit type already exists with name: " + name);
        }

        OrganizationalUnitType unitType = new OrganizationalUnitType();
        unitType.setName(name);
        return organizationalUnitTypeRepository.save(unitType);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationalUnitType findById(Long id) {
        OrganizationalUnitType unit = organizationalUnitTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OrganizationalUnitType not found with id: " + id
                ));
        return unit;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationalUnitType> findByName(String name) {
        return organizationalUnitTypeRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationalUnitType> findAll() {
        return organizationalUnitTypeRepository.findAll();
    }

    @Override
    public OrganizationalUnitType updateOrganizationalUnitType(Long id, String name) {
        OrganizationalUnitType unitType = organizationalUnitTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizational unit type not found with id: " + id));

        if (!unitType.getName().equals(name) && existsByName(name)) {
            throw new RuntimeException("Organizational unit type already exists with name: " + name);
        }

        unitType.setName(name);
        return organizationalUnitTypeRepository.save(unitType);
    }

    @Override
    public void deleteOrganizationalUnitType(Long id) {
        if (!organizationalUnitTypeRepository.existsById(id)) {
            throw new RuntimeException("Organizational unit type not found with id: " + id);
        }
        organizationalUnitTypeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return organizationalUnitTypeRepository.existsByName(name);
    }

    // DTO methods
    @Override
    public OrganizationalUnitTypeResponseDTO createOrganizationalUnitType(OrganizationalUnitTypeRequestDTO requestDto) {
        if (existsByName(requestDto.getName())) {
            throw new RuntimeException("Organizational unit type already exists with name: " + requestDto.getName());
        }

        OrganizationalUnitType unitType = organizationalUnitTypeMapper.toEntity(requestDto);
        OrganizationalUnitType savedUnitType = organizationalUnitTypeRepository.save(unitType);
        return organizationalUnitTypeMapper.toResponseDTO(savedUnitType);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Optional<OrganizationalUnitType>> findByIdDto(Long id) {
        return new ApiResponse<>(true,"unit type fetched successfully",organizationalUnitTypeRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationalUnitTypeResponseDTO> findAllDto() {
        return organizationalUnitTypeRepository.findAll().stream()
                .map(organizationalUnitTypeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationalUnitTypeResponseDTO updateOrganizationalUnitType(Long id, OrganizationalUnitTypeRequestDTO requestDto) {
        OrganizationalUnitType unitType = organizationalUnitTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizational unit type not found with id: " + id));

        if (!unitType.getName().equals(requestDto.getName()) && existsByName(requestDto.getName())) {
            throw new RuntimeException("Organizational unit type already exists with name: " + requestDto.getName());
        }

        organizationalUnitTypeMapper.updateEntityFromDto(requestDto, unitType);
        OrganizationalUnitType updatedUnitType = organizationalUnitTypeRepository.save(unitType);
        return organizationalUnitTypeMapper.toResponseDTO(updatedUnitType);
    }
}
