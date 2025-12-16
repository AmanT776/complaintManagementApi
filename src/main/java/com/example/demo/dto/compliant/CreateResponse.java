package com.example.demo.dto.compliant;

import java.time.LocalDateTime;

public class CreateResponse {
    private String title;
    private String description;
    private Boolean isAnonymous;
    private Long organizationalUnitId;
    private Long category;
    private Long user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
