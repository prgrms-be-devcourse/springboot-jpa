package com.kdt.lecturejpa.domain.order_item;

import static jakarta.persistence.FetchType.*;

import java.util.Objects;

import org.aspectj.weaver.ast.Or;

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
import lombok.Setter;

@Table(name = "order_item")
@Getter
@Setter
@Entity
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private int price;
	private int quantity;

	@Column(name = "order_id",insertable=false, updatable=false)
	private String orderId;

	@Column(name = "item_id", insertable=false, updatable=false)
	private Long itemId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Item item;

	public OrderItem(int price, int quantity) {
		this.price = price;
		this.quantity = quantity;
	}

	public OrderItem() {

	}

	public void setItem(Item item) {
		if (Objects.nonNull(this.item)) {
			this.item.getOrderItems().remove(this);
		}

		this.item = item;
		item.getOrderItems().add(this);
	}

	public void setOrder(Order order) {
		if (Objects.nonNull(this.order)) {
			this.order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}

	public static OrderItem createOrderItem(Item item, int quantity) {
		OrderItem orderItem = new OrderItem(item.getPrice(), quantity);
		orderItem.setItem(item);
		item.decreaseStockQuantity(quantity);
		item.addOrderItem(orderItem);
		return orderItem;
	}
}
