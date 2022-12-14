package com.programmers.jpapractice.domain.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	private String createdBy;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;
}
