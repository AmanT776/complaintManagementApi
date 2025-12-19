package com.example.demo.service.role;

import com.example.demo.dto.role.RoleRequestDTO;
import com.example.demo.dto.role.RoleResponseDTO;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.model.Role;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleResponseDTO create(String name, String description) {
        String clean = name.trim();
        if (roleRepository.existsByName(clean)) {
            throw new DuplicateResourceException("Role already exists: " + clean);
        }

        Role role = new Role();
        role.setName(clean);
        role.setDescription(description);
        role.setIsActive(true);

        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> findAll() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO findById(Long id) {
        Role r = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Role not found ID: " + id));
        return roleMapper.toDto(r);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO findByName(String name) {
        Role r = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFound("Role not found Name: " + name));
        return roleMapper.toDto(r);
    }

    @Override
    @Transactional
    public RoleResponseDTO create(RoleRequestDTO dto) {
        String cleanName = dto.getName().trim();
        if (roleRepository.existsByName(cleanName)) {
            throw new DuplicateResourceException("Role already exists: " + cleanName);
        }

        // Use the mapper designed for RequestDTO
        Role role = roleMapper.toEntity(dto);
        role.setName(cleanName);
        // Ensure isActive is handled if null in DTO
        if (role.getIsActive() == null) role.setIsActive(true);

        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponseDTO update(Long id, RoleRequestDTO dto) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Role not found ID: " + id));

        String newName = dto.getName().trim();
        if (!existing.getName().equalsIgnoreCase(newName) && roleRepository.existsByName(newName)) {
            throw new DuplicateResourceException("Role name already taken: " + newName);
        }

        // Update existing entity with DTO values
        roleMapper.updateEntityFromDto(dto, existing);
        existing.setName(newName);

        return roleMapper.toDto(roleRepository.save(existing));
    }


    @Override
    @Transactional
    public RoleResponseDTO activateRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Role not found ID: " + id));
        role.setIsActive(true);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponseDTO deactivateRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Role not found ID: " + id));
        role.setIsActive(false);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getActiveRoles() {
        return roleMapper.toDtoList(roleRepository.findByIsActive(true));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getInactiveRoles() {
        return roleMapper.toDtoList(roleRepository.findByIsActive(false));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFound("Role not found ID: " + id);
        }
        if (userRepository.existsByRole_Id(id)) {
            throw new IllegalStateException("Role is in use by users.");
        }
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}