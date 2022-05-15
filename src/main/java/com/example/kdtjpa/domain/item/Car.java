package com.example.kdtjpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("CAR")
@Entity
public class Car extends Item {
    private int power;


    protected Car() {

    }

    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
