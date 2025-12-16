package com.example.demo.repository;

import com.example.demo.model.OrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {

    Optional<OrganizationalUnit> findByName(String name);

    List<OrganizationalUnit> findByParentId(Long parentId);

    List<OrganizationalUnit> findByUnitTypeId(Long unitTypeId);

    boolean existsByName(String name);
}