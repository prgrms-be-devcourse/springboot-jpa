package com.example.springbootjpa.domain.order;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    //fk
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "item_id")
    private Long itemId;
}
