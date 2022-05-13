package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long orderItemId;

    @Column(name="quantity", nullable = false)
    private long quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    private Order order;
}
