package com.example.mission3.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public void setOrder(Order order) {
        if(Objects.nonNull(this.order)){
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }
}
