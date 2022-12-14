package com.kdt.jpa.entity;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    private int price;
    private int stockQuantity;
}
