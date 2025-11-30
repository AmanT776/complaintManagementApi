package com.BIT.BCMS.repository;

import com.BIT.BCMS.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    // Derived query to find a user by email (for login)
    Optional<Users> findByEmail(String email);

    boolean existsByRoles_Id(Long id);

    boolean existsByDepartmentId(Long id);
}
