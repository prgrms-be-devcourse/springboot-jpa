package com.programmers.springbootjpa.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {

    private int power;

    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);

        checkPower(power);
        this.power = power;
    }

    private void checkPower(int power) {
        if (power < 1) {
            throw new IllegalArgumentException("동력은 1보다 작을 수 없습니다.");
        }
    }

    public void updatePower(int power) {
        checkPower(power);
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
