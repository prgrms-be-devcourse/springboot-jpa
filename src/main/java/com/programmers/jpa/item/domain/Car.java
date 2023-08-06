package com.programmers.jpa.item.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CAR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Car extends Item{
    private long power;

    private Car(int price, int stockQuantity, long power) {
        super(price, stockQuantity);
        this.power = power;
    }

    public static Item of(int price, int stockQuantity, long power) {
        validatePower(power);
        return new Car(price, stockQuantity, power);
    }

    private static void validatePower(long power) {
        if (power < 0) {
            throw new IllegalArgumentException(String.format("파워가 0보다 작습니다. input: %s", power));
        }
    }
}
