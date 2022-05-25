package org.prgrms.kdt.domain.order;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Table(name = "order_item")
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int price;
	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Item item;

	protected OrderItem() {
	}

	public OrderItem(int price, int quantity) {
		this(price, quantity, null, null);
	}

	public OrderItem(int price, int quantity, Order order, Item item) {
		this.price = price;
		this.quantity = quantity;
		this.order = order;
		this.item = item;
	}

	public void changeOrder(Order order) {
		if (Objects.nonNull(this.order)) {
			this.order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}

	public void changeItem(Item item) {
		if (Objects.nonNull(this.item)) {
			this.item.getOrderItems().remove(this);
		}

		this.item = item;
		item.getOrderItems().add(this);
	}

	public Long getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public Order getOrder() {
		return order;
	}

	public Item getItem() {
		return item;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("price", price)
			.append("quantity", quantity)
			.toString();
	}
}
