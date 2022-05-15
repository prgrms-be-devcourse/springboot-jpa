package com.management.item.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car extends Item {
    @Column(name = "power", nullable = false)
    private Integer power;

    public Car(String name, Integer price, Integer stockQuantity, Integer power) {
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return Objects.equals(power, car.power);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), power);
    }
}
