package com.programmers.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer price;

    private Integer quantity;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Builder
    public OrderItem(Integer price, Integer quantity, String orderId, Long itemId) {
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.itemId = itemId;
    }
}
