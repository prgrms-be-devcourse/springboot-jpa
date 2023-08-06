package com.example.weeklyjpa.domain.item;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Getter
@Table(name = "items")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    private static final int ZERO_STOCK = 0;

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    public void deductQuantity(int orderItemQuantity) {
        if( this.stockQuantity - orderItemQuantity < ZERO_STOCK){
            throw new IllegalArgumentException("재고 부족");
        }
        this.stockQuantity -= orderItemQuantity;
    }

    public void addStock(int orderItemQuantity){
        this.stockQuantity += orderItemQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


}
