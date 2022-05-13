package com.study.springbootJpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int stockQuantity;

	private Item(int price, int stockQuantity) {
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	protected Item() {
	}

	public static Item create(int price, int stockQuantity) {
		return new Item(price, stockQuantity);
	}

	public Long getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}
}
