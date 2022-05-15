package com.example.springjpa.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Order -> OrderItem : 일대다
 * Order -> Member : 다대일
 * */
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends CreationEntity {
	@Id
	@Column(name = "id")
	private String uuid;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus orderStatus;

	@Lob
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	protected Order() {
	}

	public Order(String uuid, OrderStatus orderStatus) {
		this.uuid = uuid;
		this.orderStatus = orderStatus;
	}

	public void orderedByMember(Member member) {
		if (Objects.nonNull(this.member)) {
			member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);

		orderItem.addToOrder(this);
	}
}
