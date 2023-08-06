package com.programmers.jpa.item.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity {

    private static final String FOOD = "FOOD";
    private static final String CAR = "CAR";
    private static final int MIN = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        if (price < MIN) {
            throw new IllegalArgumentException(String.format("가격이 음수입니다. input: %s", price));
        }
    }

    private static void validateStockQuantity(int stockQuantity) {
        if (stockQuantity < MIN) {
            throw new IllegalArgumentException(String.format("재고수량이 음수입니다. input: %s", stockQuantity));
        }
    }
}
