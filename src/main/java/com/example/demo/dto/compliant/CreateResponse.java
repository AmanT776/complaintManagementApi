package com.example.demo.dto.compliant;

import java.time.LocalDateTime;

public class CreateResponse {
    private String title;
    private String description;
    private Boolean isAnonymous;
    private long organizationalUnitId;
    private long category;
    private long user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
