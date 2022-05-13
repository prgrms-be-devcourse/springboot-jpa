package com.kdt.lecture.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {

    private String chef;

    public Food(String name, int price, int stockQuantity, String chef) {
        super(name, price, stockQuantity);
        this.chef = chef;
    }
}
