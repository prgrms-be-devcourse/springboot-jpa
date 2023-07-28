package com.example.kdtjpa.domain.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int price;

	private int stockQuantity;

	@ManyToOne
	@JoinColumn(name = "order_item_id", referencedColumnName = "id")
	private OrderItem orderItem;

	public void setOrderItem(OrderItem orderItem) {
		if (Objects.nonNull(this.orderItem)) {
			this.orderItem.getItems().remove(this);
		}

		this.orderItem = orderItem;
		orderItem.getItems().add(this);
	}
}