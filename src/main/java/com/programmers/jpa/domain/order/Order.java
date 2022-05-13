package com.programmers.jpa.domain.order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order() {}

    public Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo, Member member, List<OrderItem> orderItems) {
        this.uuid = uuid;
        this.orderDatetime = orderDatetime;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
        this.orderItems = orderItems;
    }

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getOrderDatetime() {
        return orderDatetime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getMemo() {
        return memo;
    }

    public Member getMember() {
        return member;
    }

    public void changeOrderDatetime(LocalDateTime orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void changeMemo(String memo) {
        this.memo = memo;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
