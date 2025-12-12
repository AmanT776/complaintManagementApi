package com.example.demo.service;

import com.example.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(String name, String description);

    Optional<Role> findById(Integer id);

    Optional<Role> findByName(String name);

    List<Role> findAll();

    Role updateRole(Integer id, String name, String description);

    void deleteRole(Integer id);

    boolean existsByName(String name);
}