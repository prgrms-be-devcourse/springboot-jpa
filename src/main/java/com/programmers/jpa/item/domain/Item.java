package com.programmers.jpa.item.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import org.hibernate.annotations.Formula;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
@Getter
public abstract class Item extends BaseEntity {

    private static final String FOOD = "FOOD";
    private static final String CAR = "CAR";
    private static final int MIN = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int price;
    private int stockQuantity;

    @Formula("(SELECT item_type FROM item WHERE id = id)")
    @Column(name = "item_type", insertable = false, updatable = false)
    private String itemType;

    protected Item() {
    }

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

    public String getChef() {
        if (!this.itemType.equals(FOOD)) {
            throw new IllegalArgumentException("해당 상품은 음식이 아닙니다.");
        }
        return null;
    }

    public Long getPower() {
        if (!this.itemType.equals(CAR)) {
            throw new IllegalArgumentException("해당 상품은 자동차가 아닙니다.");
        }
        return null;
    }
}
