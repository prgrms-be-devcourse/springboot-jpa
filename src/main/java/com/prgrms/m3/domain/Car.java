package com.prgrms.m3.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("CAR")
@NoArgsConstructor
public class Car extends Item {
    private int power;

    public Car(int power, int price, int stockQuantity) {
        super(price, stockQuantity);
        this.power = power;
    }
}