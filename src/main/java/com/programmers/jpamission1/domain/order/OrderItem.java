package com.programmers.jpamission1.domain.order;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Item item;

	public OrderItem(int price, int quantity, Order order, Item item) {
		this.price = price;
		this.quantity = quantity;
		updateOrder(order);
		updateItem(item);
	}

	public void updateOrder(Order order) {
		if (Objects.nonNull(this.order)) {
			this.order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}

	public void updateItem(Item item) {
		if (Objects.nonNull(this.item)) {
			this.item.getOrderItems().remove(this);
		}

		this.item = item;
		item.getOrderItems().add(this);
	}

	public void updatePrice(int price) {
		this.price = price;
	}

	public void updateQuantity(int quantity) {
		this.quantity = quantity;
	}
}
