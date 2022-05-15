package com.prgrms.lec_jpa.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    protected BaseEntity() {

    }

    protected BaseEntity(LocalDateTime createdAt, String createdBy) {

        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
