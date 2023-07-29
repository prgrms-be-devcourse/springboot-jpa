package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_datetime")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Order() {
    }

    public Order(String memo, Member member) {
        this.orderDatetime = LocalDateTime.now();
        this.orderStatus = OrderStatus.OPENED;
        this.memo = memo;
        this.member = member;
    }
}
