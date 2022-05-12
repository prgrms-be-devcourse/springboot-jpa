package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(int quantity, Order order, Item item) {
        this.quantity = quantity;
        this.order = order;
        this.item = item;
        this.price = item.getPrice() * quantity;
    }

    public void setOrder(Order order) {
        if(Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.setOrderItem(this);
    }

}
