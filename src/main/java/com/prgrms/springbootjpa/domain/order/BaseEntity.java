package com.prgrms.springbootjpa.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;
}
