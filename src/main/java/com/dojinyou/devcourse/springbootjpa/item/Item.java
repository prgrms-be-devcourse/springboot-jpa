package com.dojinyou.devcourse.springbootjpa.item;

import com.dojinyou.devcourse.springbootjpa.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long price;

    private Long stockQuantity;

    protected Item() {
    }

    public Item(long price, long stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void addItemStock(long quantity) {
        stockQuantity += quantity;
    }

    public void soldItem() {
        stockQuantity--;
    }

    public void soldItem(long quantity) {
        stockQuantity -= quantity;
    }
}
