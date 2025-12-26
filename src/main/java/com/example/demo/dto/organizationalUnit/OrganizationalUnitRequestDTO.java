package com.example.demo.dto.organizationalUnit;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationalUnitRequestDTO {

    @NotBlank(message = "Unit Name is required")
    private String name;

    @NotBlank(message = "Abbreviation is required")
    private String abbreviation;

    @NotNull(message = "Unit Type ID is required")
    private Long unitTypeId;

    private Long parentId; // Optional (Roots like 'University' have no parent)

    private String unitEmail;

    @Pattern(regexp = "^\\+251\\d{9}$", message = "Phone must be +251 followed by 9 digits")
    private String phoneNumber;

    private String remarks;

    public OrganizationalUnitRequestDTO(String bit, Object o, int i, String mail, String s, String bitCampus) {
    }

    public OrganizationalUnitRequestDTO(String bahirdarInstituteOfTechnology, String bit, Long unitTypeId, int i, String mail, String phoneNumber, String bitCampus) {
    }
}