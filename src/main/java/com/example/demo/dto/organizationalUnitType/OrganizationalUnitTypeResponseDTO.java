package com.example.demo.dto.organizationalUnitType;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class OrganizationalUnitTypeResponseDTO {
    private Long id;
    private String name;
    private String description;
//    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
