package com.programmers.jpa.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items;

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item) {
        item.setOrderItem(this);
    }

    public OrderItem() {}

    public OrderItem(int price, int quantity, Order order, List<Item> items) {
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.items = items;
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

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Item> getItems() {
        return items;
    }

    public Order getOrder() {
        return order;
    }
}
