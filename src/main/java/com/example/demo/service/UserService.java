package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Basic CRUD operations
    UserDto createUser(CreateUserDto createUserDto);

    UserDto updateUser(Integer id, UpdateUserDto updateUserDto);

    void deleteUser(Integer id);

    Optional<UserDto> getUserById(Integer id);

    Optional<UserDto> getUserByEmail(String email);

    List<UserDto> getAllUsers();

    Page<UserDto> getAllUsers(Pageable pageable);

    Page<UserDto> searchUsers(String search, Pageable pageable);

    // Role-based queries
    List<UserDto> getUsersByRole(String roleName);

    List<UserDto> getActiveUsersByRole(String roleName);

    List<UserDto> getAllAdmins();

    List<UserDto> getAllStaff();

    List<UserDto> getAllRegularUsers();

    // Organizational unit queries
    List<UserDto> getUsersByOrganizationalUnit(Integer unitId);

    // User status management
    UserDto activateUser(Integer id);

    UserDto deactivateUser(Integer id);

    List<UserDto> getActiveUsers();

    List<UserDto> getInactiveUsers();

    // Password management
    void changePassword(Integer userId, ChangePasswordDto changePasswordDto);

    void resetPassword(Integer userId, String newPassword);

    // Validation methods
    boolean existsByEmail(String email);

    boolean existsByStudentId(String studentId);

    // Utility methods
    UserDto convertToDto(User user);

    User convertToEntity(CreateUserDto createUserDto);
}