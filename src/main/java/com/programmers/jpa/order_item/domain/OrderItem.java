package com.programmers.jpa.order_item.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import com.programmers.jpa.item.domain.Item;
import com.programmers.jpa.order.domain.Order;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(Order order, Item item) {
        this.order = order;
        this.item = item;
    }
}
