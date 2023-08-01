package com.example.springbootjpaweekly3.domain.entity;

import com.example.springbootjpaweekly3.domain.contant.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "total_price")
    private long totalPrice;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderItem(Long id, long totalPrice, int totalQuantity, Item item, Order order) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.item = item;
        this.order = order;
    }
}
