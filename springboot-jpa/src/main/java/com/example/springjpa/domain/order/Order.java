package com.example.springjpa.domain.order;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
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

    protected Order() {
    }

    public Order(String uuid, String memo, OrderStatus orderStatus, LocalDateTime orderDateTime, Long memberId) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.memberId = memberId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMemo() {
        return memo;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public Long getMemberId() {
        return memberId;
    }
}
