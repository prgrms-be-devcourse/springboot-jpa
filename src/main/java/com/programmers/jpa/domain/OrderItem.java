package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    private int quantity;

    @Column(name = "order_id") // fk
    private String orderId;

    @Column(name = "item_id") // fk
    private Long itemId;

    @Builder
    private OrderItem(int price, int quantity, String orderId, Long itemId) {
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.itemId = itemId;
    }
}
