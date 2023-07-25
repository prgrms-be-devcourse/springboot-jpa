package com.example.springbootjpa.domain.item;

import com.example.springbootjpa.golbal.ErrorCode;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Entity
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    protected Item(int price, int stockQuantity) {
        validateItemPrice(price);
        validateItemQuantity(stockQuantity);
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    private void validateItemPrice(int price) {
        if (price < 0) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_ITEM_PRICE);
        }
    }

    private void validateItemQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_ITEM_QUANTITY);
        }
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new InvalidDomainConditionException(ErrorCode.OUT_OF_STOCK);
        }
        this.stockQuantity = restStock;
    }
}
