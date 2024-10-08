package com.sparta.delivery.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false, nullable = true, length = 45)
    private UUID createdBy;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(nullable = true, length = 45)
    private UUID updatedBy;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @Column(nullable = true, length = 45)
    private UUID deletedBy;

    public void markDeleted(UUID userId) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userId;
    }

}
