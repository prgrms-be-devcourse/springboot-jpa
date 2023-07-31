package com.blackdog.springbootjpa.domain.order.model;

import com.blackdog.springbootjpa.domain.customer.model.Customer;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(
            OrderStatus orderStatus,
            Customer customer
    ) {
        this.orderStatus = orderStatus;
        this.customer = customer;
    }

    public void addOrderItem(OrderItem orderItem) {
        if (!orderItems.contains(orderItem)) {
            orderItems.add(orderItem);
        }
    }

}