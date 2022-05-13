package com.kdt.springbootjpa.item.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item {
    private int power;

    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }
}
