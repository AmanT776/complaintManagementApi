package com.example.demo.repository;

import com.example.demo.model.Compliant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompliantRepository  extends JpaRepository<Compliant,Integer> {
}
