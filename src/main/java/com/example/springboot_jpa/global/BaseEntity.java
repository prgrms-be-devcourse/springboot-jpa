package com.example.springboot_jpa.global;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Column (columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column (columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
