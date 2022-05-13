package org.programmers.springbootjpa.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        this.order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
