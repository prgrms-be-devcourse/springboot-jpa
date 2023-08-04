package com.programmers.week.item.domain;

import com.programmers.week.exception.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("CAR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item {

    private static final long MIN_POWER = 0;
    private static final long MAX_POWER = 1000000;
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
        if (power < MIN_POWER || power > MAX_POWER) {
            throw new IllegalStateException(String.format(Message.POWER_PRICE_IS_WRONG + "%s", power));
        }
    }

}
