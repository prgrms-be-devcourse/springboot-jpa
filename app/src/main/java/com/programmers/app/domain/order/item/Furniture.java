package com.programmers.app.domain.order.item;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FURNITURE")
@Entity
public class Furniture extends Item {
	private long width;
	private long heigt;

	@Builder
	public Furniture(int price, int stockQuantity, long width, long height, String createdBy, LocalDateTime createdAt) {
		super(price, stockQuantity, createdBy, createdAt);
		this.width = width;
		this.heigt = height;
	}
}
