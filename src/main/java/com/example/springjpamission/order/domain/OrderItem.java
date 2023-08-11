package com.example.springjpamission.order.domain;

import com.example.springjpamission.gobal.BaseEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "order_items")
@Entity
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Price price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    protected OrderItem() {}

    public OrderItem(Price price, Order order, Item items) {
        this.price = price;
        this.order = order;
        this.item = items;
    }

    public Long getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }

    public void changeOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void changeItem(Item item) { // 방금 아까 봤던 오더
        this.item = item;
    }

}
