package com.devcourse.springbootjpa.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@Builder
public class Order {
	@Id
	@Column(name = "id")
	private String uuid;

	@Column(name = "memo")
	private String memo;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus orderStatus;

	@Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
	private LocalDateTime orderDateTime;

	@Column(name = "member_id", insertable = false, updatable = false)
	private long memberID;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;

	protected Order() {
	}

	public void setMember(Member member) {
		if (Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}

	public void changeOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void changeMemo(String memo) {
		this.memo = memo;
	}
}
