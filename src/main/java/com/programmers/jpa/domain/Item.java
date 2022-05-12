package com.programmers.jpa.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;

    public void setOrderItem(OrderItem orderItem) {
        if (Objects.nonNull(this.orderItem)) {
            this.orderItem.getItems().remove(this);
        }

        this.orderItem = orderItem;
        orderItem.getItems().add(this);
    }

    public Item() {}

    public Item(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
