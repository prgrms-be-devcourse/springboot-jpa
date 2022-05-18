package com.programmers.jpa.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int orderPrice;
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private OrderItem(Item item, int orderPrice, int quantity) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }

    public static OrderItem createOrderItem(Item item, int orderPrice, int quantity) {
        item.removeStock(quantity);
        return new OrderItem(item, orderPrice, quantity);
    }

    public void setOrder(Order order) {
        order.getOrderItems().add(this);
        this.order = order;
    }
}

