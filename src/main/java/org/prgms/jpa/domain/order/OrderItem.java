package org.prgms.jpa.domain.order;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int price;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Item item;

	@Builder
	public OrderItem(int price, OrderStatus orderStatus) {
		this.price = price;
		this.orderStatus = orderStatus;
	}

	//Order 연관관계 편의 메서드 START
	public void setOrder(Order order){
		if(Objects.nonNull(this.order)){
			order.getOrderItems().remove(this);
		}

		this.order = order;
		order.getOrderItems().add(this);
	}

	public void setItem(Item item) {
		if(Objects.nonNull(this.item)){
			this.item.getOrderItems().remove(this);
		}

		this.item = item;
		item.getOrderItems().add(this);
	}
	// 연관관계 편의 메서드 END



}
