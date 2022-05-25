package com.programmers.app.domain.order.item;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FOOD")
@Entity
public class Food extends Item {
	private String chef;

	@Builder
	public Food(int price, int stockQuantity, String chef, String createdBy, LocalDateTime createdAt) {
		super(price, stockQuantity, createdBy, createdAt);
		this.chef = chef;
	}

}
