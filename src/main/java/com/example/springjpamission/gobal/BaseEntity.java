package com.example.springjpamission.gobal;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private LocalDateTime createAt;

    @Column(name = "LAST_UPDATED_AT", nullable = false)
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createAt = now;
        lastUpdatedAt = now;
    }
    @PreUpdate
    public void preUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

}
