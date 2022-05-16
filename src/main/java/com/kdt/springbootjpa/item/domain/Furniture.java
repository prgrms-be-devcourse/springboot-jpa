package com.kdt.springbootjpa.item.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {
    private int width;
    private int height;

    protected Furniture() {
    }

    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }
}
