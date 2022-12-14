package com.prgrms.m3.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("CAR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item {
    private int power;

    public Car(int power, int price, int stockQuantity) {
        super(price, stockQuantity);
        this.power = power;
    }

    public void changePower(int power) {
        this.power = power;
    }
}