package com.example.kdtjpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("FOOD")
@Entity
public class Food extends Item {
    private String chef;

    protected Food() {

    }

    public Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }
}
