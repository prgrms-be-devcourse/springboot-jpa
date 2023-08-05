package com.programmers.jpa.base.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime lastModifiedAt;

}
