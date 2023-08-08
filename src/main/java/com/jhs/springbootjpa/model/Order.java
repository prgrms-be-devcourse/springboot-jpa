package com.jhs.springbootjpa.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(STRING)
    private OrderStatus orderStatus;
    private String memo;
    private LocalDateTime orderDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {
    }

    public Order(Long id, String memo, Customer customer) {
        this.id = id;
        this.memo = memo;
        this.customer = customer;
        this.orderStatus = OrderStatus.ORDERED;
        this.orderDateTime = LocalDateTime.now();
    }

    public void addItem(Item item, int itemCount) {
        this.orderItems.add(new OrderItem(null, itemCount, this, item));
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
