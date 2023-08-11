package com.example.springjpamission.gobal;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
public abstract class BaseEntity {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Column(updatable = false, nullable = false)
    private String createAt;

    @Column(nullable = false)
    private String lastUpdatedAt;

    protected BaseEntity() { }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createAt = now.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        lastUpdatedAt = now.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

}
