package com.BIT.BCMS.repository;

import com.BIT.BCMS.entities.Role;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

    Optional<Role> findByName(@NotBlank(message = "Role name cannot be empty") String name);
}