package com.programmers.app.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

import com.programmers.app.domain.BaseEntity;
import com.programmers.app.domain.order.member.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity{

	@Id
	@Column(name = "order_id")
	private UUID id;

	@Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
	private LocalDateTime orderDatetime;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Lob
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "order")
	private final List<OrderItem> orderItems = new ArrayList<>();

	@Builder
	public Order(UUID id, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo,
			String createdBy, LocalDateTime createdAt) {
		super(createdBy, createdAt);
		this.id = id;
		this.orderDatetime = orderDatetime;
		this.orderStatus = orderStatus;
		this.memo = memo;
	}

	public void setMember(Member member) {
		if (Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setOrder(this);
	}
}
