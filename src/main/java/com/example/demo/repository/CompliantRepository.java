package com.example.demo.repository;

import com.example.demo.model.Compliant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompliantRepository extends JpaRepository<Compliant, Long> {
    Optional<Compliant> findByReferenceNumber(String referenceNumber);
    boolean existsByCategoryId(Long id);

    List<Compliant> findByOrganizationalUnitId(Long organizationalunitId);
}
