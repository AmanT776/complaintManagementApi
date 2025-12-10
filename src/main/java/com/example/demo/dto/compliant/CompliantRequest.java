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
    private Boolean is_anonymous;
    private int organizational_unit_id;
}
