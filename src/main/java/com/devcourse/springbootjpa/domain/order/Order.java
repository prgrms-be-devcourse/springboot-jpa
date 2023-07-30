package com.devcourse.springbootjpa.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String memo;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus orderStatus;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime orderDateTime;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Builder
	public Order(String memo, OrderStatus orderStatus, LocalDateTime orderDateTime, Member member,
			List<OrderItem> orderItems) {
		this.memo = memo;
		this.orderStatus = orderStatus;
		this.orderDateTime = orderDateTime;
		this.member = member;
		this.orderItems = orderItems;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void changeOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void changeMemo(String memo) {
		this.memo = memo;
	}
}
