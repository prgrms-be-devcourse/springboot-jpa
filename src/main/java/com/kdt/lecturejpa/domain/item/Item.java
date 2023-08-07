package com.kdt.lecturejpa.domain.item;

import java.util.List;

import com.kdt.lecturejpa.domain.order_item.OrderItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "stock_quantity", nullable = false)
	private int stockQuantity;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItems;

	public Item(int price, int stockQuantity, String name) {
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.name = name;
	}

	public void decreaseStockQuantity(int decreaseAmount) {
		this.stockQuantity = this.stockQuantity - decreaseAmount;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.attachItem(this);
	}
}
