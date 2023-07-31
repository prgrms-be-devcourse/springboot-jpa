package com.example.springjpamission.order.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@DiscriminatorValue("CAR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item {

    private int power;

    public Car(int power) {
        this.power = power;
    }

    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }

}
