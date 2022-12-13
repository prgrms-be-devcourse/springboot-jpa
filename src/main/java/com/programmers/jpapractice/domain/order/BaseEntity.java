package com.programmers.jpapractice.domain.order;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	private String createdBy;

	private LocalDateTime createdAt;
}
