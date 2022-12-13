package com.programmers.jpamission1.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity{

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String uuid;

	@Column(name = "order_datetime", columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime orderDatetime;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", length = 20, nullable = false)
	private OrderStatus orderStatus;

	@Lob
	@Column(name = "memo", nullable = true)
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems;

	public Order(LocalDateTime orderDatetime, OrderStatus orderStatus, String memo,
		Member member) {
		this.orderDatetime = orderDatetime;
		this.orderStatus = orderStatus;
		this.memo = memo;
		updateMember(member);
	}

	public void updateMember(Member member) {
		if(Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.updateOrder(this);
		orderItems.add(orderItem);
	}

	public void updateOrderDatetime(LocalDateTime orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public void updateOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void updateMemo(String memo) {
		this.memo = memo;
	}
}

