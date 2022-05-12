package org.prgrms.kdt.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Table(name = "item")
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int price;
	private int stockQuantity;

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItems = new ArrayList<>();

	protected Item() {
	}

	public Item(int price, int stockQuantity) {
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public Long getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("price", price)
			.append("stockQuantity", stockQuantity)
			.toString();
	}
}
