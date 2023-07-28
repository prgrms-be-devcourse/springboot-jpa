package com.devcourse.springbootjpa.domain.order;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "order_item")
@Getter
@AllArgsConstructor
@Builder
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private int price;

	private int quantity;

	@ManyToOne
	private Order order;

	@ManyToOne
	private Item item;

	protected OrderItem() {
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
