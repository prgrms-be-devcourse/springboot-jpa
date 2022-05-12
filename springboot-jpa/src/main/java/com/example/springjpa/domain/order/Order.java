package com.example.springjpa.domain.order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    protected Order() {
    }

    public Order(String memo, OrderStatus orderStatus, LocalDateTime orderDateTime, Member member, List<OrderItem> orderItems) {
        this.uuid = UUID.randomUUID().toString();
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.member = member;
        this.orderItems = orderItems;
    }

    public Order(String memo, OrderStatus orderStatus, LocalDateTime orderDateTime, Member member) {
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

    public Member getMember() {
        return member;
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

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
