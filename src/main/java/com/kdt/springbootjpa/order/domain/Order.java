package com.kdt.springbootjpa.order.domain;

import com.kdt.springbootjpa.common.entity.BaseEntity;
import com.kdt.springbootjpa.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {
    }

    public Order(String uuid, String memo, Member member, List<OrderItem> orderItems) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = OrderStatus.ACCEPTED;
        this.orderDatetime = LocalDateTime.now();
        this.member = member;
        for (OrderItem orderItem : orderItems) {
            addOrderItem(orderItem);
        }
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    private void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
