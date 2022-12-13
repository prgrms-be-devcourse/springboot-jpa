package com.programmers.jpamission1.domain.order;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;

	public void updateCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void updateCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
