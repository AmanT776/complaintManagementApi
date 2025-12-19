package com.example.demo.service.user.userserviceimpl;

import com.example.demo.dto.organizationalUnit.OrganizationalUnitResponseDTO;
import com.example.demo.dto.user.*;
import com.example.demo.dto.role.RoleDTO; // Import RoleDTO
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.RoleMapper; // Import RoleMapper
import com.example.demo.model.OrganizationalUnit;
import com.example.demo.model.Role;
import com.example.demo.model.User;
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

    @Override
    public UserDto registerUser(RegisterDto registerDto) {
        if (existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + registerDto.getEmail());
        }

        // Wrap DTO from Service in Optional to use orElseThrow
        RoleDTO roleDto = Optional.ofNullable(roleService.findByName("USER"))
                .orElseThrow(() -> new RuntimeException("Default USER role not found."));

        User user = userMapper.toEntity(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(roleMapper.toEntity(roleDto)); // Convert DTO to Entity

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (existsByEmail(createUserDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + createUserDto.getEmail());
        }

        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        // Fetch DTO and Map to Entity
        RoleDTO roleDto = Optional.ofNullable(roleService.findById(createUserDto.getRoleId()))
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + createUserDto.getRoleId()));
        user.setRole(roleMapper.toEntity(roleDto));

        if (createUserDto.getOrganizationalUnitId() != null) {
            OrganizationalUnitResponseDTO unitDto = Optional.ofNullable(organizationalUnitService.getUnitById(createUserDto.getOrganizationalUnitId()))
                    .orElseThrow(() -> new RuntimeException("Organizational unit not found with id: " + createUserDto.getOrganizationalUnitId()));
            OrganizationalUnit unit = new OrganizationalUnit();
            unit.setId(unitDto.getId());
            user.setOrganizationalUnit(unit);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (updateUserDto.getEmail() != null && !updateUserDto.getEmail().equals(user.getEmail())) {
            if (existsByEmail(updateUserDto.getEmail())) {
                throw new RuntimeException("Email already exists: " + updateUserDto.getEmail());
            }
        }

        userMapper.updateEntityFromDto(updateUserDto, user);

        if (updateUserDto.getRoleId() != null) {
            RoleDTO roleDto = Optional.ofNullable(roleService.findById(updateUserDto.getRoleId()))
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + updateUserDto.getRoleId()));
            user.setRole(roleMapper.toEntity(roleDto));
        }

        if (updateUserDto.getOrganizationalUnitId() != null) {
            OrganizationalUnitResponseDTO unitDto = Optional.ofNullable(organizationalUnitService.getUnitById(updateUserDto.getOrganizationalUnitId()))
                    .orElseThrow(() -> new RuntimeException("Organizational unit not found with id: " + updateUserDto.getOrganizationalUnitId()));
            OrganizationalUnit unit = new OrganizationalUnit();
            unit.setId(unitDto.getId());
            user.setOrganizationalUnit(unit);
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
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