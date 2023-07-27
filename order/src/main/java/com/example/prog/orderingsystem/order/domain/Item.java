package com.example.prog.orderingsystem.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "capacity", nullable = false)
    private Long capacity;

    @Lob
    @Column(name = "description")
    private String description;

    @Builder
    private Item(String name, Long price, Long capacity, String description) {
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.description = description;
    }

    protected Item() {

    }
}
