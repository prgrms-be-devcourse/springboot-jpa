package com.kdt.lecture.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
