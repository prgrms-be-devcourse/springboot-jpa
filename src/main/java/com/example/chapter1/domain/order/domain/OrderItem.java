package com.example.chapter1.domain.order.domain;

import com.example.chapter1.domain.base.BaseIdEntity;
import com.example.chapter1.domain.item.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem extends BaseIdEntity {

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = LAZY)
    private Order order;

    @ManyToOne(fetch = LAZY)
    private Item item;

    public OrderItem(int price, int quantity, Order order, Item item) {
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.item = item;
    }

    public static OrderItem create(int price, int quantity, Order order, Item item) {
        return new OrderItem(price, quantity, order, item);
    }
}
