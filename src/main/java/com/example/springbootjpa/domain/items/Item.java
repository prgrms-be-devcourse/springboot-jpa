package com.example.springbootjpa.domain.items;

import com.example.springbootjpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type", length = 255)
@Table(name = "items")
@Entity
public abstract class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    private String uuid;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity_in_stock")
    private int quantityInStock;

    @Column(name = "price")
    private int price;

    public Item(String uuid, String productName, int quantityInStock, int price) {
        super(LocalDateTime.now());
        this.uuid = uuid;
        this.productName = productName;
        this.quantityInStock = quantityInStock;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public synchronized void reduceQuantityInStockBy(int amount) {
        if (this.quantityInStock - amount < 0)
            throw new IllegalArgumentException();
        this.quantityInStock -= amount;
    }

    public String getUuid() {
        return uuid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
