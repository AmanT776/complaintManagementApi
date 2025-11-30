package com.BIT.BCMS.entities;

import com.BIT.BCMS.enums.ComplaintStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "complaints")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // human-friendly public reference (unique)
    @Column(name = "reference_number", nullable = false, unique = true, length = 100)
    private String referenceNumber;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ComplaintStatus status = ComplaintStatus.RECEIVED;

    // If the complaint is submitted anonymously, submitter is null and this flag is true
    @Column(nullable = false)
    private boolean anonymous = false;

    // optional contact info for anonymous submission
    @Column(name = "contact_info", length = 200)
    private String contactInfo;

    // the user who submitted (null for anonymous complaints)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitter_id")
    @JsonBackReference("user-complaints")
    private Users submitter;

    // department and category for the complaint
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonBackReference("dept-complaints")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference("cat-complaints")
    private Category category;

    // attachments
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("complaint-attachments")
    private List<Attachment> attachments = new ArrayList<>();

    // timestamps
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        // generate a public ref if not provided
        if (referenceNumber == null || referenceNumber.isBlank()) {
            referenceNumber = generateReference();
        }
        createdAt = updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    private String generateReference() {
        // Example: UC-<UUID short>
        return "UC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

