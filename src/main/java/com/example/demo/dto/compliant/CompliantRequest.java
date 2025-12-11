package com.example.demo.dto.compliant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompliantRequest {
    private String title;
    private String description;
    private Boolean isAnonymous;
    private int organizationalUnitId;
    private int categoryId;
}
