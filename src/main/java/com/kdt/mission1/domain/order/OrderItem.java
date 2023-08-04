package com.kdt.mission1.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
}
