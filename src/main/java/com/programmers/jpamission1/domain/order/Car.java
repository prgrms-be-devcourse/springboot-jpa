package com.programmers.jpamission1.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("CAR")
public class Car extends Item{

	@Column(name = "power",nullable = false)
	private long power;

	public Car(int price, int stockQuantity, long power) {
		super(price, stockQuantity);
		this.power = power;
	}

	public void updatePower(long power) {
		this.power = power;
	}
}
