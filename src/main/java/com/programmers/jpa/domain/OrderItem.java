package com.programmers.jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    @Column(name = "order_id") // fk
    private String order_id;

    @Column(name = "item_id") // fk
    private Long item_id;

    public OrderItem() {}

    public OrderItem(Long id, int price, int quantity, String order_id, Long item_id) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.order_id = order_id;
        this.item_id = item_id;
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrder_id() {
        return order_id;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
