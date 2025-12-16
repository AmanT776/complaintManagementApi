package com.example.demo.mapper;



import com.example.demo.dto.organizationalUnit.OrganizationalUnitRequestDTO;
import com.example.demo.dto.organizationalUnitType.OrganizationalUnitResponseDTO;
import com.example.demo.model.OrganizationalUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrganizationalUnitMapper {

    // Entity -> Response DTO
    @Mapping(source = "unitType.id", target = "unitTypeId")
    @Mapping(source = "unitType.name", target = "unitTypeName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    OrganizationalUnitResponseDTO toResponseDTO(OrganizationalUnit unit);

    // Request DTO -> Entity (Creation)
    // We ignore complex associations here and set them in the Service layer
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "unitType", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "complaints", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(source = "currentUserId", target = "createUserId")
    @Mapping(source = "currentUserId", target = "updateUserId")
    OrganizationalUnit toEntity(OrganizationalUnitRequestDTO dto);

    // Request DTO -> Entity (Update)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "unitType", ignore = true) // Handled in service if changed
    @Mapping(target = "parent", ignore = true)   // Handled in service if changed
    @Mapping(target = "createUserId", ignore = true)
    @Mapping(source = "currentUserId", target = "updateUserId")
    void updateEntityFromDto(OrganizationalUnitRequestDTO dto, @MappingTarget OrganizationalUnit entity);
}