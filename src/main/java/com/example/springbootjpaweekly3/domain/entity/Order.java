package com.example.springbootjpaweekly3.domain.entity;


import com.example.springbootjpaweekly3.domain.contant.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_datetime")
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus status;

    @Column(columnDefinition = "LONGTEXT")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(Long id, LocalDateTime orderDateTime, OrderStatus status, String memo, Member member) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.status = status;
        this.memo = memo;
        this.member = member;
    }
}
