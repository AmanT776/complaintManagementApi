package com.BIT.BCMS.repository;


import com.BIT.BCMS.entities.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByReferenceNumber(String referenceNumber);

    boolean existsByCategoryId(Long id);

    boolean existsByDepartmentId(Long id);
}
