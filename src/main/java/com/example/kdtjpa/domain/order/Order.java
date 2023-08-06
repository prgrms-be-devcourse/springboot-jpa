package com.example.kdtjpa.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
	@Id
	@Column(name = "id")
	private String uuid;

	@Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
	private LocalDateTime orderDatetime;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Lob
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id") // referencedColumnName는 FK가 상대편의 PK가 아닌 컬럼을 외래키로 지정하기 위해 사용
	private Member member;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();

	protected Order() {

	}

	public Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo, Member member) {
		this.uuid = uuid;
		this.orderDatetime = orderDatetime;
		this.orderStatus = orderStatus;
		this.memo = memo;
		this.member = member;
	}

	public Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo) {
		this.uuid = uuid;
		this.orderDatetime = orderDatetime;
		this.orderStatus = orderStatus;
		this.memo = memo;
	}

	public void setMember(Member member) {
		if(Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.addItem(this);
	}

	@Override
	public String toString() {
		return "Order{" +
			"uuid='" + uuid + '\'' +
			'}';
	}

	public String getUuid() {
		return uuid;
	}

	public LocalDateTime getOrderDatetime() {
		return orderDatetime;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public String getMemo() {
		return memo;
	}

	public Member getMember() {
		return member;
	}

}