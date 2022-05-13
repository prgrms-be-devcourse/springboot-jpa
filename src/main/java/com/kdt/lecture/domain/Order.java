package com.kdt.lecture.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@AllArgsConstructor
@Builder
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

    public void addMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.addOrder(this);
    }

    public Order createOrder(Member member, String memo,OrderItem... orderItems) {
        Order order = Order.builder()
                .member(member)
                .orderItems(List.of(orderItems))
                .memo(memo)
                .orderDateTime(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
        order.setCreatedBy(member.getName());
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
