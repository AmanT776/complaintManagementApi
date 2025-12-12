package com.example.demo.service;

import com.example.demo.model.OrganizationalUnit;

import java.util.List;
import java.util.Optional;

public interface OrganizationalUnitService {

    OrganizationalUnit createOrganizationalUnit(String name, Integer parentId, Integer unitTypeId);

    Optional<OrganizationalUnit> findById(Integer id);

    Optional<OrganizationalUnit> findByName(String name);

    List<OrganizationalUnit> findAll();

    List<OrganizationalUnit> findByParentId(Integer parentId);

    List<OrganizationalUnit> findByUnitTypeId(Integer unitTypeId);

    OrganizationalUnit updateOrganizationalUnit(Integer id, String name, Integer parentId, Integer unitTypeId);

    void deleteOrganizationalUnit(Integer id);

    boolean existsByName(String name);
}