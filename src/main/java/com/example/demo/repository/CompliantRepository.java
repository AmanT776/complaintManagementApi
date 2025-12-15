package com.example.demo.repository;

import com.example.demo.model.Compliant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompliantRepository extends JpaRepository<Compliant, Integer> {
    Optional<Compliant> findByReferenceNumber(String referenceNumber);
}
