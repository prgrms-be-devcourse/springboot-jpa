package com.prgrms.be.jpa.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {
    @Column(name = "power")
    private int power;

    public Car(int price, int stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }
}
