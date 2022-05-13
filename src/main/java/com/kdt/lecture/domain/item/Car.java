package com.kdt.lecture.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("CAR")
public class Car extends Item{

    private int power;

    public Car(String name, int price, int stockQuantity, int power) {
        super(name, price, stockQuantity);
        this.power = power;
    }
}
