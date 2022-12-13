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
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {

	@Column(name = "width", nullable = false)
	private long width;

	@Column(name = "height", nullable = false)
	private long height;

	public Furniture(int price, int stockQuantity, long width, long height) {
		super(price, stockQuantity);
		this.width = width;
		this.height = height;
	}

	public void updateWidth(long width) {
		this.width = width;
	}

	public void updateHeight(long height) {
		this.height = height;
	}
}
