package com.programmers.jpa.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private int price;
    private int stockQuantity;

    private Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static Item of(String name, int price, int stockQuantity) {
        return new Item(name, price, stockQuantity);
    }

    public void removeStock(int stockQuantity) {
        this.stockQuantity -= stockQuantity;
    }
}
