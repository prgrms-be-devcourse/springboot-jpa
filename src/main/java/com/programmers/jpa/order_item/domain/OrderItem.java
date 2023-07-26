package com.programmers.jpa.order_item.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import com.programmers.jpa.item.domain.Item;
import com.programmers.jpa.order.domain.Order;
import jakarta.persistence.*;

@Entity
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {
    }
}
