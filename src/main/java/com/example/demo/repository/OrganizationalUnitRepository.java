package com.example.demo.repository;


import com.example.demo.model.OrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {

    boolean existsByName(String name);

    List<OrganizationalUnit> findByName(String name);

    // Get all units of a specific type (e.g., all Faculties)
    List<OrganizationalUnit> findByUnitTypeName(String name);

    // Get all children of a specific parent (e.g., all Departments in a Faculty)
    List<OrganizationalUnit> findByParentId(Long parentId);

    // Find top-level units (e.g., University level)
    List<OrganizationalUnit> findByParentIdIsNull();

}