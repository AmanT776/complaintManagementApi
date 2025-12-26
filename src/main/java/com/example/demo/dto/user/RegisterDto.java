package com.example.demo.dto.user;

import com.example.demo.model.OrganizationalUnit;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @NotBlank
    @Size(max = 30)
    private String firstName;

    @NotBlank
    @Size(max = 30)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 20)
    private String studentId;

    // ✅ Correct field
    private Long organizationalUnitId;
}
