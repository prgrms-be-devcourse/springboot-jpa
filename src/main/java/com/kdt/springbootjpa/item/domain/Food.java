package com.kdt.springbootjpa.item.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {
    private String chef;

    public Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }
}
