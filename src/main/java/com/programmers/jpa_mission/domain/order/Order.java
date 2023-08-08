package com.programmers.jpa_mission.domain.order;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

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
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {

    }

    public Order(LocalDateTime orderDatetime, OrderStatus orderStatus, String memo) {
        this.uuid = UUID.randomUUID().toString();
        this.orderDatetime = orderDatetime;
        this.orderStatus = orderStatus;
        this.memo = memo;
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

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Member getMember() {
        return member;
    }

    public void mappingMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.mappingOrder(this);
    }
}
