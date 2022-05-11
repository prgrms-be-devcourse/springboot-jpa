package com.example.lecturejpa.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name="item")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name= "price")
	private int price;
	@Column(name ="stock_quantity")
	private int stockQuantity;

	@OneToMany(mappedBy = "item")
	private final List<OrderItem> orderItems = new ArrayList<>();

	protected Item() {
	}

	public Item(int price, int stockQuantity) {
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setItem(this);
	}
}
