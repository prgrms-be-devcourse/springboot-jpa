package com.programmers.jpapractice.domain.order;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int quantity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Item item;

	public OrderItem(int price, int quantity) {
		this.price = price;
		this.quantity = quantity;
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

		this.item = item;
		item.getOrderItems().add(this);
	}
}
