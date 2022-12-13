package com.programmers.jpamission1.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item{

	@Column(name = "chef", nullable = false)
	private String chef;

	public Food(int price, int stockQuantity, String chef) {
		super(price, stockQuantity);
		this.chef = chef;
	}

	public void updateChef(String chef) {
		this.chef = chef;
	}
}
