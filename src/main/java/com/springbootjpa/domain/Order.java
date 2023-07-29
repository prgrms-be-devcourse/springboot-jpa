package com.springbootjpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Customer customer;

    @Embedded
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Customer customer, Delivery delivery) {
        this.customer = customer;
        this.delivery = delivery;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }
}
