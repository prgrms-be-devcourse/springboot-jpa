package com.programmers.june.jpastudy.domain.item.entity;

import com.programmers.june.jpastudy.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @Builder
    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void removeStockQuantity(int orderQuantity) {
        int remainQuantity = this.stockQuantity - orderQuantity;

        if (remainQuantity < 0) {
            throw new RuntimeException("Need More Stock!");
        }

        this.stockQuantity = remainQuantity;
    }

    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }
}
