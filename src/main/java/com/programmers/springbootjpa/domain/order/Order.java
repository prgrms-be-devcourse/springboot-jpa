package com.programmers.springbootjpa.domain.order;

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
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(OrderStatus orderStatus, String memo, Member member, List<OrderItem> orderItems) {
        this.orderDatetime = LocalDateTime.now();
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;

        updateOrderItems(orderItems);
    }

    public void updateOrderItems(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> orderItem.updateOrder(this));
    }

    public void updateMember(Member member) {
        if (Objects.nonNull(this.member)) {
            List<Order> orders = this.member.getOrders();
            orders.remove(this);
        }

        this.member = member;
        List<Order> orders = member.getOrders();
        orders.add(this);
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
