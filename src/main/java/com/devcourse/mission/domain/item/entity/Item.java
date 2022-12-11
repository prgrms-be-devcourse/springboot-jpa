package com.devcourse.mission.domain.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Table(name = "items")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    public Item(String title, int price, int stockQuantity) {
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void increaseQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        if (this.stockQuantity - quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 주문 할 수 있는 상품 재고가 부족합니다.");
        }
        this.stockQuantity -= quantity;
    }

    public boolean isSame(Item item) {
        return Objects.equals(this.id, item.id);
    }

    public boolean isSameId(long itemId) {
        return this.id == itemId;
    }
}
