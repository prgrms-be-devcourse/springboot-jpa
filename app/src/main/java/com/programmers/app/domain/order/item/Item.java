package com.programmers.app.domain.order.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.programmers.app.domain.BaseEntity;
import com.programmers.app.domain.order.OrderItem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "item")
public abstract class Item extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private int price;
	private int stockQuantity;

	@OneToMany(mappedBy = "item")
	private final List<OrderItem> orderItems = new ArrayList<>();

	public Item(int price, int stockQuantity, String createdBy, LocalDateTime createdAt) {
		super(createdBy, createdAt);
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setItem(this);
	}

}