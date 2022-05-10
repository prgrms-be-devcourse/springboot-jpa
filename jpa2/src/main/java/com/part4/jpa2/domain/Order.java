package com.part4.jpa2.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id
    @Column(name ="id")
    private String uuid;
    private String memo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    // member_fk
    private Long memberId;

    @Builder
    public Order(String uuid, String memo, OrderStatus orderStatus, LocalDateTime orderDatetime, Long memberId) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDatetime = orderDatetime;
        this.memberId = memberId;
    }
}
