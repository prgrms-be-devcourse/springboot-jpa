package com.example.springjpamission.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("CAR")
@Entity
public class Car extends Item {

    private static final int ZERO = 0;

    @Column
    private int power;

    protected Car() { }

    public Car(Price price, int stockQuantity, int power) {
        super(price, stockQuantity);
        validatePower(power);
        this.power = power;
    }

    private void validatePower(int power) {
        if(power < ZERO) {
            throw new IllegalArgumentException();
        }
    }

    public int getPower() {
        return power;
    }

}
