package com.BIT.BCMS.repository;

import com.BIT.BCMS.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    // (Custom queries can be added if needed)
}
