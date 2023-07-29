package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private int price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(int price, Order order, Item item) {
        this.price = price;
        this.order = order;
        this.item = item;
        this.orderStatus = OrderStatus.OPENED;
    }
}
