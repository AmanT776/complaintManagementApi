package com.example.demo.repository;


import com.example.demo.model.OrganizationalUnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationalUnitTypeRepository extends JpaRepository<OrganizationalUnitType, Long> {
    // Basic checks needed for validation
    boolean existsByName(String name);
}