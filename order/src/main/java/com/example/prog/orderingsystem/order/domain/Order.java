package com.example.prog.orderingsystem.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Lob
    @Column(name = "memo", nullable = true)
    private String memo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(Long id, Long customerid, String memo) {
        this.id = id;
        this.customerId = customerid;
        this.memo = memo;
    }

    @Builder
    private Order(Long customerId, String memo, List<OrderItem> orderItems) {
        this(null, customerId, memo);

        if (orderItems!=null && !orderItems.isEmpty()) {
            this.orderItems.addAll(orderItems);
        }
    }

    public void addItem(Item item) {
        OrderItem orderItem = new OrderItem(this, item);
         this.orderItems.add(orderItem);
    }
}
