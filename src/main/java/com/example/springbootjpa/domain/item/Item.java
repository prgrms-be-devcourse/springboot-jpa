package com.example.springbootjpa.domain.item;

import com.example.springbootjpa.golbal.ErrorCode;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "stock_quantity", nullable = false)
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

    public void decreaseStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new InvalidDomainConditionException(ErrorCode.OUT_OF_STOCK);
        }
        this.stockQuantity = restStock;
    }

    public void validateRequestQuantity(int quantity) {
        if (stockQuantity < quantity) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_ORDER_ITEM_QUANTITY);
        }
    }
}
