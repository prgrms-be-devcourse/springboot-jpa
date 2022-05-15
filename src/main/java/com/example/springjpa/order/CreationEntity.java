package com.example.springjpa.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class CreationEntity {
	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;

	public CreationEntity() {
		this.createdAt = LocalDateTime.now();
	}
}
