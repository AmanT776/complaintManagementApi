package com.example.demo.mapper;


import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeRequestDTO;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitTypeResponseDTO;
import com.example.demo.model.OrganizationalUnitType;
import org.mapstruct.*;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationalUnitTypeMapper {

    OrganizationalUnitTypeResponseDTO toResponseDTO(OrganizationalUnitType unitType);

    // Request DTO -> Entity (Creation)
    @Mapping(target = "id", ignore = true)          // JPA will generate
    @Mapping(target = "createdAt", ignore = true)   // handled in @PrePersist
    @Mapping(target = "updatedAt", ignore = true)   // handled in @PreUpdate
    OrganizationalUnitType toEntity(OrganizationalUnitTypeRequestDTO dto);

    // Request DTO -> Entity (Update)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)          // don’t overwrite primary key
    @Mapping(target = "createdAt", ignore = true)   // keep original creation time
    @Mapping(target = "updatedAt", ignore = true)   // updated automatically
    void updateEntityFromDto(OrganizationalUnitTypeRequestDTO dto, @MappingTarget OrganizationalUnitType entity);

}

