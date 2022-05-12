package com.example.demo.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "item_id") //fk
    private Long itemId;

    public void setOrder(Order order) {
        if(Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

}
