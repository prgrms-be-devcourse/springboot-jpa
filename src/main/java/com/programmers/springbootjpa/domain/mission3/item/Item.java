package com.programmers.springbootjpa.domain.mission3.item;

import com.programmers.springbootjpa.domain.mission3.BaseEntity;
import com.programmers.springbootjpa.global.exception.InvalidRequestValueException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table
@Entity
public abstract class Item extends BaseEntity {

    private static final int MINIMUM_PRICE_LIMIT = 0;
    private static final int MINIMUM_STOCK_QUANTITY_LIMIT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    public Item(int price, int stockQuantity) {
        checkPrice(price);
        checkPrice(stockQuantity);

        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    private void checkPrice(int price) {
        if (price < MINIMUM_PRICE_LIMIT) {
            throw new InvalidRequestValueException("금액은 0원보다 적을 수 없습니다.");
        }
    }

    private void checkStockQuantity(int stockQuantity) {
        if (stockQuantity < MINIMUM_STOCK_QUANTITY_LIMIT) {
            throw new InvalidRequestValueException("재고 수량은 0개보다 적을 수 없습니다.");
        }
    }

    public void update(int price, int stockQuantity) {
        checkPrice(price);
        this.price = price;

        checkStockQuantity(stockQuantity);
        this.stockQuantity = stockQuantity;
    }
}
