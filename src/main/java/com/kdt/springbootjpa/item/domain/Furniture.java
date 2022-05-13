package com.kdt.springbootjpa.item.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FURNITURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Furniture extends Item {
    private int width;
    private int height;

    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }
}
