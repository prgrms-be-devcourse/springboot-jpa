package com.devcourse.springbootjpa.domain.order;

import java.util.List;

import com.devcourse.springbootjpa.exception.InvalidQuantityException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "item")
@Builder
@Getter
@AllArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private int price;

	private int stockQuantity;

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItems;

	protected Item() {
	}

	public Item(int price, int stockQuantity) {
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public void addOrderItem(OrderItem orderItem) {
		reduceQuantity(orderItem.getQuantity());
		orderItem.setItem(this);
	}

	public void reduceQuantity(int quantity) {
		if (stockQuantity < quantity) {
			throw new InvalidQuantityException("재고 수량이 부족합니다");
		}
		stockQuantity -= quantity;
	}
}
