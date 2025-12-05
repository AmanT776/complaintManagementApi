package com.BIT.BCMS.repository;

import com.BIT.BCMS.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Optional<Users> findByEmail(String email);
    boolean existsByRole_Id(Long id);
    boolean existsByDepartmentId(Long id);
}

