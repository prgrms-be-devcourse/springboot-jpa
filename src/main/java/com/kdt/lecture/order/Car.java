package com.kdt.lecture.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("CAR")
public class Car extends Item{
    private int power;

    public Car() {}

    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }

}
