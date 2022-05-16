package com.kdt.springbootjpa.order.domain;

import com.kdt.springbootjpa.common.entity.BaseEntity;
import com.kdt.springbootjpa.item.domain.Item;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(int price, int quantity, Item item) {
        this.price = price;
        this.quantity = quantity;
        this.item = item;
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
    }
}
