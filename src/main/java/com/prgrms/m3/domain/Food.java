package com.prgrms.m3.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("FOOD")
@NoArgsConstructor
public class Food extends Item {
    private String chef;

    public Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }
}