package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long price;
    private int quantity;

    @Column(name = "order_id") //fk
    private String orderId;
    @Column(name = "item_id") //fk
    private Long itemId;

}
