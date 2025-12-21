package com.example.demo.dto.permission;

import lombok.Data;

@Data
public class PermissionRequestDTO {
    private String name;
    private String description;
    private String category;
    private Boolean isActive;
}
