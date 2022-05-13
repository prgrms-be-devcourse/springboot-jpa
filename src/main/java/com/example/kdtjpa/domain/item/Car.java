package com.example.kdtjpa.domain.item;

import lombok.NoArgsConstructor;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("CAR")
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Car extends Item {
    private int power;

    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
