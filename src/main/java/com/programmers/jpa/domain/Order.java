package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_datetime")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(String memo, Member member) {
        nullCheck(member);
        this.orderDatetime = LocalDateTime.now();
        this.orderStatus = OrderStatus.OPENED;
        this.memo = memo;
        this.member = member;
    }

    private void nullCheck(Member member) {
        if (Objects.isNull(member)) {
            throw new IllegalArgumentException("Order member is mandatory");
        }
    }

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELED;
        orderItems.forEach(OrderItem::cancelOrderItem);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}
