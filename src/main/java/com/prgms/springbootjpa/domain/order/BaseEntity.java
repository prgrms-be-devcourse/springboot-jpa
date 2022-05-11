package com.prgms.springbootjpa.domain.order;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    protected BaseEntity() {
    }

    public BaseEntity(String createdBy, LocalDateTime createdAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
