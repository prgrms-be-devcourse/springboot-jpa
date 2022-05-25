package com.study.springbootJpa.domain;

import java.time.LocalDateTime;
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

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@Column(name = "id")
	private String uuid;

	@Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
	private LocalDateTime orderDateTime;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Lob
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	private Order(String uuid, LocalDateTime orderDateTime, OrderStatus orderStatus, String memo) {
		this.uuid = uuid;
		this.orderDateTime = orderDateTime;
		this.orderStatus = orderStatus;
		this.memo = memo;
	}

	protected Order() {
	}

	public static Order create(String uuid, LocalDateTime orderDateTime, OrderStatus orderStatus, String memo) {
		return new Order(uuid, orderDateTime, orderStatus, memo);
	}

	public void setMember(Member member) {
		if (Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public Member getMember() {
		return member;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public String getUuid() {
		return uuid;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public String getMemo() {
		return memo;
	}

}
