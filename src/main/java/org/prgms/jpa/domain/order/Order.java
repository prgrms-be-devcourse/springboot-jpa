package org.prgms.jpa.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDateTime orderDatetime;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Lob
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Builder
	public Order(OrderStatus orderStatus, String memo) {
		this.orderDatetime = LocalDateTime.now();
		this.orderStatus = orderStatus;
		this.memo = memo;
	}

	// 연관관계 편의 메서드 START
	// Order에 이미 다른 member가 매핑되어있는 경우 그 member의 orderlist에서 제거?
	public void setMember(Member member) {
		if (this.member != null && Objects.nonNull(member)) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void setOrderItems(OrderItem orderItem) {
		orderItem.setOrder(this);
	}

	// 연관관계 편의 메서드 END

}
