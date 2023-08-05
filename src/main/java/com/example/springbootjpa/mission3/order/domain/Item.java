package com.example.springbootjpa.mission3.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    public Item() {
    }

    public Item(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

}
