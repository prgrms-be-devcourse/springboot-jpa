package com.programmers.app.domain.order;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.programmers.app.domain.BaseEntity;
import com.programmers.app.domain.order.item.Item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private int price;

	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY) // fk
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@Builder
	public OrderItem(int price, int quantity, String createdBy, LocalDateTime createdAt) {
		super(createdBy, createdAt);
		this.price = price;
		this.quantity = quantity;
	}

	public void setOrder(Order order) {
		if (Objects.nonNull(order)) {
			this.order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}

	public void setItem(Item item) {
		if (Objects.nonNull(item)) {
			this.item.getOrderItems().remove(this);
		}

		this.item = item;
		item.getOrderItems().add(this);
	}
}
