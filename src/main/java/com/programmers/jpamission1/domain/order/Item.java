package com.programmers.jpamission1.domain.order;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "stockQuantity", nullable = false)
	private int stockQuantity;

	public Item(int price, int stockQuantity) {
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems;

	public void addOrderItems(OrderItem orderItem) {
		orderItem.updateItem(this);
		orderItems.add(orderItem);
	}

	public void updatePrice(int price) {
		this.price = price;
	}

	public void updateStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
}
