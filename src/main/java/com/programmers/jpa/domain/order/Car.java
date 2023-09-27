package com.programmers.jpa.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("CAR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item {
    @Column(name = "power")
    private int power;

    @Builder
    private Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }
}
