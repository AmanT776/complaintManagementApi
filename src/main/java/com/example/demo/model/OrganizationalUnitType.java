package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)//to trace createUserId and updateUserId fields w/o manual handling
@Entity
@Table(name = "ORGANIZATIONAL_UNIT_TYPE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrganizationalUnitType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String publicId;

    @NotBlank(message = "Name cannot be empty")
    @Column(unique = true, nullable = false)
    private String name; // e.g. "FACULTY", "DEPARTMENT"

    @Column(name = "DESCRIPTION")
    private String description;

    // 1 = active, 0 = inactive
    private Integer status;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", updatable = false)
    private LocalDateTime createTime;


    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME")
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        this.publicId = (this.publicId == null) ? UUID.randomUUID().toString() : this.publicId;
        this.status = (this.status == null) ? 1 : this.status;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}