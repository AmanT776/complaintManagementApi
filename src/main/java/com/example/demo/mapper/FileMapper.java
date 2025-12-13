package com.example.demo.mapper;

import com.example.demo.dto.file.CreateFile;
import com.example.demo.model.Files;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    Files mapCreateFileToFile(CreateFile createFile);
}
