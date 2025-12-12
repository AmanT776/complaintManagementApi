package com.example.demo.repository;

import com.example.demo.model.OrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Integer> {

    Optional<OrganizationalUnit> findByName(String name);

    List<OrganizationalUnit> findByParentId(Integer parentId);

    List<OrganizationalUnit> findByUnitTypeId(Integer unitTypeId);

    boolean existsByName(String name);
}