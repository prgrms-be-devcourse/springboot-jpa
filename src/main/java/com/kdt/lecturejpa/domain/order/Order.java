package com.kdt.lecturejpa.domain.order;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
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

	// member_fk
	@Column(name = "member_id")
	private Long memberId;

}
