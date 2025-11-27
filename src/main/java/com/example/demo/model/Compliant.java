package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Compliant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String reference_number;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(columnDefinition = "default null")
    private boolean is_anonymous;
    public enum Status{
        PENDING,
        RECEIVED,
        UNDER_REVIEW,
        RESOLVED,
        CLOSED
    }
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;



}
