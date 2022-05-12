package org.prgrms.kdt.domain.order;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Table(name = "orders")
@Entity
public class Order {

	@Id
	@Column(name = "id")
	private String uuid;

	@Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
	private LocalDateTime orderDatetime;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Lob
	private String memo;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	protected Order() {
	}

	public Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo) {
		this.uuid = uuid;
		this.orderDatetime = orderDatetime;
		this.orderStatus = orderStatus;
		this.memo = memo;
	}

	//== 연관관계 편의 메서드 ==//
	public void changeMember(Member member) {
		if (Objects.nonNull(this.member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("uuid", uuid)
			.append("orderDatetime", orderDatetime)
			.append("orderStatus", orderStatus)
			.append("memo", memo)
			.toString();
	}
}
