package com.example.demo.repository;

import com.example.demo.model.Compliant;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompliantRepository extends JpaRepository<Compliant, Long> {
    Optional<Compliant> findByReferenceNumber(String referenceNumber);

//    List<Compliant> findByUser(User user);
//
//    List<Compliant> findByUserId(Long userId);
//
//    //List<Compliant> findByStatus(Compliant.Status status);
//
//    List<Compliant> findByIsAnonymous(Boolean isAnonymous);
//
//    Optional<Compliant> findByReferenceNumber(String referenceNumber);
//
//    @Query("SELECT c FROM Compliant c WHERE " +
//            "(:search IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//            "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//            "LOWER(c.referenceNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
//    Page<Compliant> findBySearchTerm(@Param("search") String search, Pageable pageable);
//
//    @Query("SELECT c FROM Compliant c WHERE c.user.id = :userId")
//    Page<Compliant> findByUserId(@Param("userId") Long userId, Pageable pageable);
//
//    @Query("SELECT c FROM Compliant c WHERE c.status = :status")
//    Page<Compliant> findByStatus(@Param("status") Compliant.Status status, Pageable pageable);
//
}