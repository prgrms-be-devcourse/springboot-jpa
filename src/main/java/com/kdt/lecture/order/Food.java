package com.kdt.lecture.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;

    public Food() {}

    public Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }

}
