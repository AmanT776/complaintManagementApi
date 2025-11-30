package com.BIT.BCMS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Lightweight Department DTO — excludes users and complaints lists intentionally.
 */
@Data
public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Department name is required")
    private String name;
}

