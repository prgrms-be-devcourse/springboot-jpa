package com.programmers.springbootjpa.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private int price;
    private int quantity;

    // fk
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "item_id")
    private Long itemId;

}