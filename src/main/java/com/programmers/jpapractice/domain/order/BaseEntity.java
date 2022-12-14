package com.programmers.jpapractice.domain.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseEntity {

	private String createdBy;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;
}
