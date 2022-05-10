package com.example.jpasettingpractice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;

    public void setOrderItem(OrderItem orderItem) {
        if(Objects.nonNull(this.orderItem)) {
            orderItem.getItems().remove(this);
        }
        this.orderItem = orderItem;
        orderItem.getItems().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return price == item.price && stockQuantity == item.stockQuantity
                && id.equals(item.id) && orderItem.equals(item.orderItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, stockQuantity, orderItem);
    }
}
