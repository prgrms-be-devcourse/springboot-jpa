package com.kdt.lecture.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity{

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "order_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Singular
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public Order(String memo) {
        this.uuid = UUID.randomUUID().toString();
        this.orderDateTime = LocalDateTime.now();
        this.orderStatus = OrderStatus.ORDER;
        this.memo = memo;
    }

    public void addMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        this.member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.addOrder(this);
    }

    public static Order createOrder(Member member, String memo, OrderItem... orderItems) {
        Order order = new Order(memo);
        order.setCreatedBy(member.getName());

        order.addMember(member);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELLED;
        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : this.orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
