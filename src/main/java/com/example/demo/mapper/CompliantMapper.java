package com.example.demo.mapper;

import com.example.demo.dto.compliant.CompliantRequest;
import com.example.demo.model.Compliant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompliantMapper {
    Compliant mapCompliantRequestToCompliant(CompliantRequest compliantRequest);
}
