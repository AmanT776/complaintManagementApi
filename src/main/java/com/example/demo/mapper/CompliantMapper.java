package com.example.demo.mapper;

import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.CreateResponse;
import com.example.demo.model.Compliant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompliantMapper {
    @Mapping(target = "files", ignore = true)
    Compliant mapCompliantRequestToCompliant(CreateRequest createRequest);
    CreateResponse mapCompliantToCompliantResponse(Compliant compliant);
}
