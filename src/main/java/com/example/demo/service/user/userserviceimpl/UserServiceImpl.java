package com.example.demo.service.user.userserviceimpl;

import com.example.demo.dto.organizationalUnit.OrganizationalUnitResponseDTO;
import com.example.demo.dto.role.RoleResponseDTO;
import com.example.demo.dto.role.RoleRequestDTO;

import com.example.demo.dto.user.*;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.RoleMapper; // Import RoleMapper
import com.example.demo.model.OrganizationalUnit;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.OrganizationalUnitRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import com.example.demo.service.role.RoleService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final OrganizationalUnitService organizationalUnitService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper; // Added RoleMapper
    private final RoleRepository roleRepository;
    private final OrganizationalUnitRepository unitRepository;

    @Override
    public UserDto registerUser(RegisterDto registerDto) {
        if (existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + registerDto.getEmail());
        }

        // Fetch the actual managed Entity from DB
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default USER role not found."));

        User user = userMapper.toEntity(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(role);
        user.setIsActive(true);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (existsByEmail(createUserDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + createUserDto.getEmail());
        }

        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        // Handle Role Relationship
        Role role = roleRepository.findById(createUserDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + createUserDto.getRoleId()));
        user.setRole(role);

        // Handle Org Unit Relationship
        if (createUserDto.getOrganizationalUnitId() != null) {
            OrganizationalUnit unit = unitRepository.findById(createUserDto.getOrganizationalUnitId())
                    .orElseThrow(() -> new RuntimeException("Unit not found"));
            user.setOrganizationalUnit(unit);
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Logic for email uniqueness
        if (updateUserDto.getEmail() != null && !updateUserDto.getEmail().equals(user.getEmail())) {
            if (existsByEmail(updateUserDto.getEmail())) {
                throw new RuntimeException("Email already exists: " + updateUserDto.getEmail());
            }
        }

        userMapper.updateEntityFromDto(updateUserDto, user);

        if (updateUserDto.getRoleId() != null) {
            Role role = roleRepository.findById(updateUserDto.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        if (updateUserDto.getOrganizationalUnitId() != null) {
            OrganizationalUnit unit = unitRepository.findById(updateUserDto.getOrganizationalUnitId())
                    .orElseThrow(() -> new RuntimeException("Unit not found"));
            user.setOrganizationalUnit(unit);
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> searchUsers(String search, Pageable pageable) {
        return userRepository.findBySearchTerm(search, pageable).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersByRole(String roleName) {
        return userRepository.findByRoleName(roleName).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getActiveUsersByRole(String roleName) {
        return userRepository.findByRoleNameAndIsActive(roleName, true).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllAdmins() {
        return userRepository.findAllAdmins().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllStaff() {
        return userRepository.findAllStaff().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllRegularUsers() {
        return userRepository.findAllUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersByOrganizationalUnit(Long unitId) {
        return userRepository.findByOrganizationalUnitId(unitId).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setIsActive(true);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setIsActive(false);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getActiveUsers() {
        return userRepository.findByIsActive(true).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getInactiveUsers() {
        return userRepository.findByIsActive(false).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public UserDto updateProfile(Long id, ProfileUpdateDto profileUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Validate email uniqueness if email is being changed
        if (profileUpdateDto.getEmail() != null && !profileUpdateDto.getEmail().equals(user.getEmail())) {
            if (existsByEmail(profileUpdateDto.getEmail())) {
                throw new RuntimeException("Email already exists: " + profileUpdateDto.getEmail());
            }
        }

        // Validate student ID uniqueness if student ID is being changed
        if (profileUpdateDto.getStudentId() != null && !profileUpdateDto.getStudentId().equals(user.getStudentId())) {
            if (existsByStudentId(profileUpdateDto.getStudentId())) {
                throw new RuntimeException("Student ID already exists: " + profileUpdateDto.getStudentId());
            }
        }

        // Update only safe fields (no role, org unit, or isActive)
        if (profileUpdateDto.getFirstName() != null) {
            user.setFirstName(profileUpdateDto.getFirstName());
        }
        if (profileUpdateDto.getLastName() != null) {
            user.setLastName(profileUpdateDto.getLastName());
        }
        if (profileUpdateDto.getEmail() != null) {
            user.setEmail(profileUpdateDto.getEmail());
        }
        if (profileUpdateDto.getPhoneNumber() != null) {
            user.setPhoneNumber(profileUpdateDto.getPhoneNumber());
        }
        if (profileUpdateDto.getStudentId() != null) {
            user.setStudentId(profileUpdateDto.getStudentId());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }





    @Override
    public void changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password incorrect");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByStudentId(String studentId) {
        return studentId != null && userRepository.existsByStudentId(studentId);
    }
}