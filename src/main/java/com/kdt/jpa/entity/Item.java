package com.kdt.jpa.entity;

import jakarta.persistence.*;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @ManyToOne(fetch = LAZY)
    private OrderItem orderItem;

    public void setOrderItem(OrderItem orderItem) {
        if (Objects.nonNull(this.orderItem)) {
            this.orderItem.getItems().remove(this);
        }
        orderItem.getItems().add(this);
    }
}
