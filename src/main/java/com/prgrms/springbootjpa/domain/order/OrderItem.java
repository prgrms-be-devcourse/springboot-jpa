package com.prgrms.springbootjpa.domain.order;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public OrderItem() {
    }

    public OrderItem(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        if(Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if(Objects.nonNull(this.item)) {
            this.item.orderItems.remove(this);
        }

        this.item = item;
        item.orderItems.add(this);
    }
}
