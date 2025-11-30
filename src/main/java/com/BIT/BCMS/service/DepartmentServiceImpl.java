package com.BIT.BCMS.service;


import com.BIT.BCMS.entities.Department;
import com.BIT.BCMS.dto.DepartmentDTO;
import com.BIT.BCMS.exceptions.DuplicateResourceException;
import com.BIT.BCMS.exceptions.ResourceNotFoundException;
import com.BIT.BCMS.mapper.DepartmentMapper;
import com.BIT.BCMS.repository.ComplaintRepository;
import com.BIT.BCMS.repository.DepartmentRepository;
import com.BIT.BCMS.repository.UserRepository;
import com.BIT.BCMS.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Department service implementation that uses DepartmentMapper for conversions.
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        return departmentMapper.toDtoList(departmentRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO findById(Long id) {
        Department d = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return departmentMapper.toDto(d);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO findByName(String name) {
        Department d = departmentRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + name));
        return departmentMapper.toDto(d);
    }

    @Override
    @Transactional
    public DepartmentDTO create(DepartmentDTO dto) {
        String clean = dto.getName().trim();
        if (departmentRepository.existsByName(clean)) {
            throw new DuplicateResourceException("Department with name '" + clean + "' already exists");
        }
        dto.setName(clean);
        Department saved = departmentRepository.save(departmentMapper.toEntity(dto));
        return departmentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public DepartmentDTO update(Long id, DepartmentDTO dto) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        String newName = dto.getName().trim();
        if (!existing.getName().equalsIgnoreCase(newName) && departmentRepository.existsByName(newName)) {
            throw new DuplicateResourceException("Department with name '" + newName + "' already exists");
        }

        dto.setName(newName);
        departmentMapper.updateEntityFromDto(dto, existing);
        Department saved = departmentRepository.save(existing);
        return departmentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found with id: " + id);
        }

        if (userRepository.existsByDepartmentId(id)) {
            throw new IllegalStateException("Cannot delete department with id " + id + " — users are assigned to it");
        }

        if (complaintRepository.existsByDepartmentId(id)) {
            throw new IllegalStateException("Cannot delete department with id " + id + " — complaints reference it");
        }

        departmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }
}

