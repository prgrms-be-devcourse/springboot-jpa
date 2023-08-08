package com.jhs.springbootjpa.model;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private int price;
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(Long id, int quantity, Order order, Item item) {
        this.id = id;
        this.quantity = quantity;
        this.order = order;
        this.item = item;
        this.price = item.getPrice();
    }
}
