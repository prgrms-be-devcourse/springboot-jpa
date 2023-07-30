package com.devcourse.springbootjpa.domain.order;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private int price;

	private int quantity;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Order order;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Item item;

	@Builder
	public OrderItem(int price, int quantity, Order order, Item item) {
		this.price = price;
		this.quantity = quantity;
		this.order = order;
		this.item = item;
	}

	public void setOrder(Order order) {
		if (Objects.nonNull(this.order)) {
			this.order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}

	public void setItem(Item item) {
		if (Objects.nonNull(this.item)) {
			this.item.getOrderItems().remove(this);
		}
		item.addOrderItem(this);
		this.item = item;
	}
}
