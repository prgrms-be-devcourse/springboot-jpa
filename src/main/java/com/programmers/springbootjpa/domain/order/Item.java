package com.programmers.springbootjpa.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table
@Entity
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    public Item(int price, int stockQuantity) {
        super(LocalDateTime.now());

        checkPrice(price);
        checkPrice(stockQuantity);

        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    private void checkPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("금액은 0원보다 적을 수 없습니다.");
        }
    }

    private void checkStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("재고 수량은 0개보다 적을 수 없습니다.");
        }
    }

    public void updatePrice(int price) {
        checkPrice(price);
        this.price = price;
    }

    public void updateStockQuantity(int stockQuantity) {
        checkStockQuantity(stockQuantity);
        this.stockQuantity = stockQuantity;
    }

    public void addStockQuantity(int quantity) {
        int result = stockQuantity + quantity;
        checkStockQuantity(result);
        this.stockQuantity = result;
    }

    public void subtractStockQuantity(int quantity) {
        int result = stockQuantity - quantity;
        checkStockQuantity(result);
        this.stockQuantity = result;
    }
}
