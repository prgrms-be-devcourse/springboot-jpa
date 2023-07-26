package com.pgms.jpa.domain.order;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_create_time", nullable = false)
    private LocalDateTime orderCreateTime;

    @Column(name = "order_update_time", nullable = false)
    private LocalDateTime orderUpdateTime;

    protected Order() {

    }

    public Order(OrderStatus orderStatus, LocalDateTime orderCreateTime, LocalDateTime orderUpdateTime) {
        this.orderStatus = orderStatus;
        this.orderCreateTime = orderCreateTime;
        this.orderUpdateTime = orderUpdateTime;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderCreateTime() {
        return orderCreateTime;
    }

    public LocalDateTime getOrderUpdateTime() {
        return orderUpdateTime;
    }
}
