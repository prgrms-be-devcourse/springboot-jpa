package com.programmers.week.base;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime lastModifiedAt;
}
