package com.example.demo.controller;

import com.example.demo.dto.user.ApiResponse;
import com.example.demo.service.user.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
public class PublicController {

    private final UserService userService;

    // Public Validation Endpoints (for registration form validation)
    @GetMapping("/validate/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> validateEmail(
            @PathVariable
            @Email(message = "Invalid email format")
            @Size(max = 50, message = "Email must not exceed 50 characters")
            String email) {

        // Check if email exists in database
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Email validation completed", exists));
    }

    @GetMapping("/validate/student-id/{studentId}")
    public ResponseEntity<ApiResponse<Boolean>> validateStudentId(
            @PathVariable
            @Size(min = 1, max = 20, message = "Student ID must be between 1 and 20 characters")
            String studentId) {

        boolean exists = userService.existsByStudentId(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student ID validation completed", exists));
    }
}