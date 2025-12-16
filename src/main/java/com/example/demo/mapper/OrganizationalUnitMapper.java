package com.example.demo.mapper;

import com.example.demo.dto.OrganizationalUnitDto;
import com.example.demo.model.OrganizationalUnit;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrganizationalUnitMapper {

    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "unitType.name", target = "unitTypeName")
    OrganizationalUnitDto toDto(OrganizationalUnit organizationalUnit);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true) // Will be set manually in service
    @Mapping(target = "unitType", ignore = true) // Will be set manually in service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrganizationalUnit toEntity(OrganizationalUnitDto organizationalUnitDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "unitType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(OrganizationalUnitDto organizationalUnitDto, @MappingTarget OrganizationalUnit organizationalUnit);
}