package com.programmers.week.item.domain;

import com.programmers.week.base.BaseEntity;
import com.programmers.week.exception.Message;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity {

    private static final int MIN_PRICE = 0;
    private static final int MIN_STOCK_QUANTITY = 0;
    private static final int MAX_STOCK_QUANTITY = 50;

    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int stockQuantity;

    protected Item(int price, int stockQuantity) {
        validatePrice(price);
        validateStockQuantity(stockQuantity);
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    private static void validatePrice(int price) {
        if (price < MIN_PRICE) {
            throw new IllegalArgumentException(String.format(Message.TOTAL_PRICE_IS_WRONG + "%s", price));
        }
    }

    private static void validateStockQuantity(int stockQuantity) {
        if (stockQuantity < MIN_STOCK_QUANTITY || stockQuantity >= MAX_STOCK_QUANTITY) {
            throw new IllegalArgumentException(String.format(Message.TOTAL_QUANTITY_IS_WRONG + "%s", stockQuantity));
        }
    }

}
