package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "member_id")  // fk
    private Long memberId;

    @Builder
    private Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo, Long memberId) {
        this.uuid = uuid;
        this.orderDatetime = orderDatetime;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.memberId = memberId;
    }
}
