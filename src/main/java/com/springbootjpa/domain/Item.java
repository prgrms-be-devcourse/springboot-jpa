package com.springbootjpa.domain;

import com.springbootjpa.exception.InValidItemException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Item {

    private static final int MIN_STOCK = 0;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private int stock;

    public Item(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    private void validateStock(int stock) {
        if (stock < MIN_STOCK) {
            throw new InValidItemException("재고수량은 0이상이여야 합니다.");
        }
    }

    public void decreaseStock(int count) {
        validateStock(stock - count);
        this.stock = stock - count;
    }

    public boolean isMoreThanStock(int count) {
        if (stock < count) {
            return true;
        }
        return false;
    }
}
