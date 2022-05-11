package com.programmers.app.domain.order.item;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("CAR")
@Entity
public class Car extends Item {
	private long power;

	@Builder
	public Car(int price, int stockQuantity, long power,String createdBy, LocalDateTime createdAt) {
		super(price, stockQuantity,createdBy,createdAt);
		this.power = power;
	}


}
