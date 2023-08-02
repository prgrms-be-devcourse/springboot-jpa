package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {
    private static final int ZERO = 0;
    private static final int NAME_LENGTH = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = NAME_LENGTH)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    public Item(String name, int price, int stockQuantity) {
        nullCheck(name);
        validateName(name);
        validatePrice(price);
        validateStockQuantity(stockQuantity);
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    private void nullCheck(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("Item name is mandatory");
        }
    }

    private void validateName(String name) {
        if (name.length() > NAME_LENGTH) {
            throw new IllegalArgumentException("Invalid item name");
        }
    }

    private void validatePrice(int price) {
        if(price < ZERO) {
            throw new IllegalArgumentException("Price must be positive or zero");
        }
    }

    private void validateStockQuantity(int stockQuantity) {
        if(stockQuantity < ZERO) {
            throw new IllegalArgumentException("Stock quantity must be positive or zero");
        }
    }

    public void decreaseStockQuantity() {
        validateStockQuantity(stockQuantity - 1);
        stockQuantity--;
    }

    public void increaseStockQuantity() {
        stockQuantity++;
    }
}
