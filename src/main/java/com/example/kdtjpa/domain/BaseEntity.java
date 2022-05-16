package com.example.kdtjpa.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    protected BaseEntity() {
    }

    protected BaseEntity(String createdBy, LocalDateTime createdAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
}
