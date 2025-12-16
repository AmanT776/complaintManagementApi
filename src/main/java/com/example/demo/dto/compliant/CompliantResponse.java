package com.example.demo.dto.compliant;

import com.example.demo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompliantResponse {
    private long id;
    private String referenceNumber;
    private String title;
    private String description;
    private Boolean isAnonymous;
    private Status status;
    private Long organizationalUnitId;
    private Long categoryId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


