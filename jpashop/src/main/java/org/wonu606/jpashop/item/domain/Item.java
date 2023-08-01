package org.wonu606.jpashop.item.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public abstract class Item {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Long id;

    protected Integer price;
    protected Integer stockQuantity;

    public Item() {
    }

    public Item(Long id, Integer price, Integer stockQuantity) {
        this.id = id;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Item(Integer price, Integer stockQuantity) {
        this(null, price, stockQuantity);
    }
}
