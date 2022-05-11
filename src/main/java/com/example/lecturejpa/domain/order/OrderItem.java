package com.example.lecturejpa.domain.order;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name = "price")
	private int price;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "order_id", insertable = false, updatable = false)
	private String orderId;
	@Column(name = "item_id", insertable = false, updatable = false)
	private Long itemId;

	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Item item;

	protected OrderItem() {
	}

	public OrderItem(int price, int quantity, Order order, Item item) {
		this.price = price;
		this.quantity = quantity;
		setOrder(order);
		setItem(item);
	}

	public void setOrder(Order order) {
		if (Objects.nonNull(this.order)) {
			this.order.getOrderItems().remove(this);
		}
		this.order = order;
		this.order.getOrderItems().add(this);
	}

	public void setItem(Item item) {
		if (Objects.nonNull(this.item)) {
			this.item.getOrderItems().remove(this);
		}
		this.item = item;
		this.item.getOrderItems().add(this);
	}
}
