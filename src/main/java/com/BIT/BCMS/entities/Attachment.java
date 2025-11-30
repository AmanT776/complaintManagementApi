package com.BIT.BCMS.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "attachments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String filename;

    @Column(name = "content_type", length = 120)
    private String contentType;

    @Column(name = "file_size")
    private Long size;

    // store the path or blob reference depending on implementation (S3 URL, filesystem path, or DB storage)
    @Column(name = "storage_path", length = 1024)
    private String storagePath;

    // Many attachments belong to one complaint
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    @JsonBackReference("complaint-attachments")
    private Complaint complaint;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private Date uploadedAt;

    @PrePersist
    protected void onCreate() {
        uploadedAt = new Date();
    }
}
