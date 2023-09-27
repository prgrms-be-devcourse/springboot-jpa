package com.kdt.lecturejpa.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.kdt.lecturejpa.domain.member.Member;
import com.kdt.lecturejpa.domain.order_item.OrderItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "memo", nullable = false)
	private String memo;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatus orderStatus;

	@Column(name = "order_datetime", columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime orderDateTime;

	// member_fk,
	@Column(name = "member_id", insertable = false, updatable = false, nullable = false)
	private Long memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Builder
	public Order(String memo, OrderStatus orderStatus, LocalDateTime orderDateTime, Long memberId) {
		this.memo = memo;
		this.orderStatus = orderStatus;
		this.orderDateTime = orderDateTime;
		this.memberId = memberId;
	}

	public Order(String memo, OrderStatus orderStatus) {
		this.memo = memo;
		this.orderStatus = orderStatus;
	}

	public static Order createOrder(OrderItem orderItem, Member member, String memo, OrderStatus orderStatus) {
		Order newOrder = new Order(memo, orderStatus);
		newOrder.addOrderItem(orderItem);
		newOrder.attachMember(member);

		return newOrder;
	}

	public void attachMember(Member member) {
		if (Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.attachOrder(this);
	}

}
