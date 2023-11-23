package com.example.kdtjpa.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("CAR")
public class Car extends Item {
    private int power;

    @Builder
    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }
}
