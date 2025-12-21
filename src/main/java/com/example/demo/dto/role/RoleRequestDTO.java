package com.example.demo.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequestDTO {

    @NotBlank(message = "Role name is required")
    private String name; // e.g. ROLE_ADMIN, ROLE_STUDENT, ROLE_COMPLAINT_OFFICER
    private Boolean isActive;
    private String description;
}
