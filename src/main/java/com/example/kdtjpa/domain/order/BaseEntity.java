package com.example.kdtjpa.domain.order;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Setter;

@MappedSuperclass
@Setter
public class BaseEntity {
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;
}
