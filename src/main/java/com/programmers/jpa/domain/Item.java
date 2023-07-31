package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    private int stockQuantity;

    public Item(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
