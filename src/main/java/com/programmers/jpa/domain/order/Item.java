package com.programmers.jpa.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "items")
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity {

    public static final int MIN_PRICE = 100;

    public static final int MIN_STOCK_QUANTITY = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    protected Item(int price, int stockQuantity) {
        validationPrice(price);
        validationStockQuantity(stockQuantity);
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    private void validationPrice(int price) {
        if (price < MIN_PRICE) {
            throw new IllegalArgumentException("상품의 가격은 100원 이상이 되어야 합니다.");
        }
    }

    private void validationStockQuantity(int stockQuantity) {
        if (stockQuantity < MIN_STOCK_QUANTITY) {
            throw new IllegalArgumentException("상품의 재고 수량은 0개 이상이 되어야 합니다.");
        }
    }
}
