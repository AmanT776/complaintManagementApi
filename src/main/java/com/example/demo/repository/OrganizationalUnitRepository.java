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

    List<OrganizationalUnit> findByUnitTypeName(String name);

    List<OrganizationalUnit> findByParentId(Long parentId);

    List<OrganizationalUnit> findByParentIdIsNull();

}