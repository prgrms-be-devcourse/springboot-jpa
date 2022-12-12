package com.example.springbootjpa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "order_item")
@Entity
public class OrderItem {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public OrderItem(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getOrderItems().remove(this);
        }

        this.item = item;
        item.getOrderItems().add(this);
    }
}
