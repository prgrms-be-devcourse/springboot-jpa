package com.example.lecturejpa.domain.order;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name = "orders")
public class Order {
	@Id
	@Column(name = "id")
	private String uuid;
	@Column(name = "memo")
	private String memo;
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;
	@Column(name = "ordered_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime orderedAt;

	//member_fk
	@Column(name = "member_id", insertable = false, updatable = false)
	private Long memberId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order")
	private final List<OrderItem> orderItems = new ArrayList<>();

	protected Order() {
	}

	public Order(String memo, OrderStatus status, Member member) {
		this.uuid = UUID.randomUUID().toString();
		this.memo = memo;
		this.status = status;
		this.orderedAt = LocalDateTime.now();
		setMember(member);
	}

	public void setMember(Member member) {
		if (Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}
		this.member = member;
		this.member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setOrder(this);
	}
}
