package com.example.demo.model;

import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Compliant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "reference_number", length = 50)
    private String referenceNumber;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(name = "is_anonymous", nullable = true,columnDefinition = "BOOLEAN")
    private Boolean isAnonymous;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    @ManyToOne
    @JoinColumn(name = "organizational_unit_id")
    private OrganizationalUnit organizationalUnit;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @OneToMany
    @JoinColumn(name = "compliant_id")
    private List<Files> files = new ArrayList<>();
}
