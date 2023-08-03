package com.kdt.lecturejpa.domain.item;

import java.util.List;

import com.kdt.lecturejpa.domain.order_item.OrderItem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="item")
@Getter
@Setter
public class Item {

	@Id
	@GeneratedValue(strategy =  GenerationType.SEQUENCE)
	private Long id;

	private int price;
	private int stockQuantity;
	private String name;

	public Item(int price, int stockQuantity, String name) {
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.name = name;
	}

	public Item() {
	}

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItems;

	public void decreaseStockQuantity(int decreaseAmount) {
		this.stockQuantity = this.stockQuantity - decreaseAmount;
	}
	public void addOrderItem(OrderItem orderItem) {
		orderItem.setItem(this);
	}
}
