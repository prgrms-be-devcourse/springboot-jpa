package com.kdt.mission1.domain.order;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "order_status")
    @Enumerated(value = STRING)
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToMany(mappedBy = "orderItem")
    private List<Item> items;
}
