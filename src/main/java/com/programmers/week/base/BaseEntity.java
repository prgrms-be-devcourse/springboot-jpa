package com.programmers.week.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime lastModifiedAt;
}
