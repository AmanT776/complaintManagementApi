package com.example.demo.controller;

import com.example.demo.dto.user.*;
import com.example.demo.service.organizationalUnit.OrganizationalUnitServiceImpl;
import com.example.demo.service.role.RoleServiceImpl;
import com.example.demo.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;
    private final RoleServiceImpl roleService;
    private final OrganizationalUnitServiceImpl organizationalUnitService;

    // User Management
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserDto createdUser = userService.createUser(createUserDto);
        return new ResponseEntity<>(ApiResponse.success("User created successfully", createdUser), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserDto> users = search != null && !search.trim().isEmpty() ?
                userService.searchUsers(search, pageable) : userService.getAllUsers(pageable);

        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(ApiResponse.success("User retrieved successfully", u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, updateUserDto);
            return ResponseEntity.ok(ApiResponse.success("User updated successfully", updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/users/{id}/activate")
    public ResponseEntity<ApiResponse<UserDto>> activateUser(@PathVariable Long id) {
        try {
            UserDto user = userService.activateUser(id);
            return ResponseEntity.ok(ApiResponse.success("User activated successfully", user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/users/{id}/deactivate")
    public ResponseEntity<ApiResponse<UserDto>> deactivateUser(@PathVariable Long id) {
        try {
            UserDto user = userService.deactivateUser(id);
            return ResponseEntity.ok(ApiResponse.success("User deactivated successfully", user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/users/{id}/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            userService.resetPassword(id, newPassword);
            return ResponseEntity.ok(ApiResponse.success("Password reset successfully", "Password reset successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // User Queries
    @GetMapping("/users/role/{roleName}")
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsersByRole(@PathVariable String roleName) {
        List<UserDto> users = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(ApiResponse.success("Users by role retrieved successfully", users));
    }

    @GetMapping("/users/staff")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllStaff() {
        List<UserDto> staff = userService.getAllStaff();
        return ResponseEntity.ok(ApiResponse.success("Staff users retrieved successfully", staff));
    }

    @GetMapping("/users/regular-users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllRegularUsers() {
        List<UserDto> users = userService.getAllRegularUsers();
        return ResponseEntity.ok(ApiResponse.success("Regular users retrieved successfully", users));
    }

    @GetMapping("/users/organizational-unit/{unitId}")
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsersByOrganizationalUnit(@PathVariable Long unitId) {
        List<UserDto> users = userService.getUsersByOrganizationalUnit(unitId);
        return ResponseEntity.ok(ApiResponse.success("Users by organizational unit retrieved successfully", users));
    }

    @GetMapping("/users/active")
    public ResponseEntity<ApiResponse<List<UserDto>>> getActiveUsers() {
        List<UserDto> users = userService.getActiveUsers();
        return ResponseEntity.ok(ApiResponse.success("Active users retrieved successfully", users));
    }

    @GetMapping("/users/inactive")
    public ResponseEntity<ApiResponse<List<UserDto>>> getInactiveUsers() {
        List<UserDto> users = userService.getInactiveUsers();
        return ResponseEntity.ok(ApiResponse.success("Inactive users retrieved successfully", users));
    }



    // Staff Access Endpoints (Read-only operations for staff)
    @GetMapping("/staff/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsersForStaff(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserDto> users = search != null && !search.trim().isEmpty() ?
                userService.searchUsers(search, pageable) : userService.getAllUsers(pageable);

        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @GetMapping("/staff/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<UserDto>> getUserByIdForStaff(@PathVariable Long id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(ApiResponse.success("User retrieved successfully", u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/staff/users/staff")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllStaffForStaff() {
        List<UserDto> staff = userService.getAllStaff();
        return ResponseEntity.ok(ApiResponse.success("Staff users retrieved successfully", staff));
    }

    @GetMapping("/staff/users/organizational-unit/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsersByOrganizationalUnitForStaff(@PathVariable Long unitId) {
        List<UserDto> users = userService.getUsersByOrganizationalUnit(unitId);
        return ResponseEntity.ok(ApiResponse.success("Users by organizational unit retrieved successfully", users));
    }
}