package com.study.springbootJpa.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private OrderStatus orderStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Item item;

	private OrderItem(int price, OrderStatus orderStatus) {
		this.price = price;
		this.orderStatus = orderStatus;
	}

	protected OrderItem() {
	}

	public static OrderItem create(int price, OrderStatus orderStatus) {
		return new OrderItem(price, orderStatus);
	}

	public void setOrder(Order order) {
		if (Objects.nonNull(this.order)) {
			this.order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public Order getOrder() {
		return order;
	}

	public Item getItem() {
		return item;
	}
}