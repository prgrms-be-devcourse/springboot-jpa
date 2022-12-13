package com.prgrms.m3.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.registerMember(member);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.changeOrderStatus(OrderStatus.OPENED);

        return order;
    }

    //연관관계 편의 메소드
    public void registerMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.addOrder(this);
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice();
        }

        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
