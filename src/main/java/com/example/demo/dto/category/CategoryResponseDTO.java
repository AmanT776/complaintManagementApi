package com.example.demo.dto.category;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private Boolean isActive;
}
