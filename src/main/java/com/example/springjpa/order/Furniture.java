package com.example.springjpa.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Furniture")
public class Furniture extends Item{
	private int width;
	private int height;

	protected Furniture() {}
	public Furniture(int price, int stockQuantity, int width, int height) {
		super(price, stockQuantity);
		this.width = width;
		this.height = height;
	}
}
