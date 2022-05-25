package com.programmers.app.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime cratedAt;


	public BaseEntity(String createdBy, LocalDateTime cratedAt) {
		this.createdBy = createdBy;
		this.cratedAt = cratedAt;
	}
}
