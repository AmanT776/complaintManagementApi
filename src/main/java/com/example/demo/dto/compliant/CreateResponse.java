package com.example.demo.dto.compliant;

import java.time.LocalDateTime;

public class CreateResponse {
    private String title;
    private String description;
    private Boolean isAnonymous;
    private int organizationalUnitId;
    private int category;
    private int user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
