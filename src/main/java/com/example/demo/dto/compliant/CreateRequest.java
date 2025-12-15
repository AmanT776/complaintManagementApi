package com.example.demo.dto.compliant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequest {
    private String title;
    private String description;
    private Boolean isAnonymous;
    private int organizationalUnitId;
    private int categoryId;
    private Integer userId;
    private List<MultipartFile> files;

    // Allow client to send userId as "null" or empty; convert to null safely
    public void setUserId(String userId) {
        if (userId == null || userId.isBlank() || "null".equalsIgnoreCase(userId.trim())) {
            this.userId = null;
        } else {
            this.userId = Integer.valueOf(userId);
        }
    }
}
