package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem {
    private static final int ZERO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(int price, Order order, Item item) {
        validatePrice(price);
        this.price = price;
        this.orderStatus = OrderStatus.OPENED;
        setOrder(order);
        setItem(item);
    }

    private void validatePrice(int price) {
        if (price < ZERO) {
            throw new IllegalArgumentException("Order item price must be positive or zero");
        }
    }

    private void setOrder(Order order) {
        this.order = order;
        order.addOrderItem(this);
    }

    private void setItem(Item item) {
        this.item = item;
        item.decreaseStockQuantity();
    }

    public void cancelOrderItem() {
        orderStatus = OrderStatus.CANCELED;
        item.increaseStockQuantity();
    }
}
