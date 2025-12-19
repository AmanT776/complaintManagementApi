package com.example.demo.dto.role;

import jakarta.validation.constraints.NotBlank;

public class RoleResponseDTO {
    private Long id;

    private String name; // e.g. ROLE_ADMIN, ROLE_STUDENT, ROLE_COMPLAINT_OFFICER
    private Boolean isActive;
    private String description;
}
