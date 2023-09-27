package com.kdt.lecturejpa.domain.order_item;

import static jakarta.persistence.FetchType.*;

import java.util.Objects;

import com.kdt.lecturejpa.domain.item.Item;
import com.kdt.lecturejpa.domain.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "order_item")
@Getter
@Entity
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
	private Order order;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
	private Item item;

	public OrderItem(int price, int quantity) {
		this.price = price;
		this.quantity = quantity;
	}

	public static OrderItem createOrderItem(Item item, int quantity) {
		OrderItem orderItem = new OrderItem(item.getPrice(), quantity);
		orderItem.attachItem(item);
		item.decreaseStockQuantity(quantity);
		item.addOrderItem(orderItem);

		return orderItem;
	}

	public void attachItem(Item item) {
		if (Objects.nonNull(this.item)) {
			this.item.getOrderItems().remove(this);
		}

		this.item = item;
		item.getOrderItems().add(this);
	}

	public void attachOrder(Order order) {
		if (Objects.nonNull(this.order)) {
			this.order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}
}
