package com.programmers.jpapractice.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int stockQuantity;

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItems = new ArrayList<>();

	public Item(String name, int price, int stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setItem(this);
	}
}
