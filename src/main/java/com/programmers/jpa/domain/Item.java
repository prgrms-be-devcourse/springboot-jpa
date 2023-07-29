package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    private int price;
    private int stockQuantity;

    protected Item() {
    }

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
