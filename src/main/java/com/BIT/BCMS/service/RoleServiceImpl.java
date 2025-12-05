package com.BIT.BCMS.service;

import com.BIT.BCMS.entities.Role;
import com.BIT.BCMS.dto.RoleDTO;
import com.BIT.BCMS.exceptions.DuplicateResourceException;
import com.BIT.BCMS.exceptions.ResourceNotFoundException;
import com.BIT.BCMS.mapper.RoleMapper;
import com.BIT.BCMS.repository.RoleRepository;
import com.BIT.BCMS.repository.UserRepository;
import com.BIT.BCMS.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Role service implementation using MapStruct mapper.
 * - Keeps mapping logic centralized in RoleMapper
 * - Keeps transactional boundaries on service methods
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
// since I used dataseeder to implement the roles i dont need this default roles so I commented it out but i kept it just to remember the old codes :)//  @PostConstruct
//    @Transactional
//    public void initializeCoreRoles() {
//        List<String> coreRoles = Arrays.asList("ADMIN", "COMP_OFFICER", "USERS");
//
//        for (String roleName : coreRoles) {
//            if (!roleRepository.existsByName(roleName)) {
//                Role newRole = Role.builder().name(roleName).build();
//                roleRepository.save(newRole);
//                System.out.println("Seeded required role: " + roleName);
//            }
//        }
//    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Role r = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return roleMapper.toDto(r);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO findByName(String name) {
        Role r = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + name));
        return roleMapper.toDto(r);
    }

    @Override
    @Transactional
    public RoleDTO create(RoleDTO dto) {
        String clean = dto.getName().trim();
        if (roleRepository.existsByName(clean)) {
            throw new DuplicateResourceException("Role with name '" + clean + "' already exists");
        }

        dto.setName(clean); // sanitize before mapping
        Role saved = roleRepository.save(roleMapper.toEntity(dto));
        return roleMapper.toDto(saved);
    }

    @Override
    @Transactional
    public RoleDTO update(Long id, RoleDTO dto) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        String newName = dto.getName().trim();
        if (!existing.getName().equalsIgnoreCase(newName) && roleRepository.existsByName(newName)) {
            throw new DuplicateResourceException("Role with name '" + newName + "' already exists");
        }

        // Use mapper to update the entity in-place (keeps other fields intact)
        dto.setName(newName);
        roleMapper.updateEntityFromDto(dto, existing);
        Role saved = roleRepository.save(existing);
        return roleMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        // Prevent deleting roles that are in use by users
        if (userRepository.existsByRole_Id(id)) {
            throw new IllegalStateException("Cannot delete role with id " + id + " — users are assigned to it");
        }
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}

