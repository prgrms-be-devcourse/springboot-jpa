package com.prgrms.lec_jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    protected BaseEntity(LocalDateTime createdAt, String createdBy) {

        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
