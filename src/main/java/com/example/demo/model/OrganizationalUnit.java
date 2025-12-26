package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrganizationalUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(name = "ABBREVIATION")
    private String abbreviation;

    @Column(name = "UNIT_EMAIL", unique = true)
    private String unitEmail;

    @Column(name = "PHONE_NUMBER")
    @Pattern(regexp = "^\\+251\\d{9}$", message = "Invalid phone number. Must start with +251.")
    private String phoneNumber;
    @Lob
    @Column(name = "REMARKS" , columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id", nullable = true)
    private OrganizationalUnit parent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_type_id",nullable = false)
    private OrganizationalUnitType unitType;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
