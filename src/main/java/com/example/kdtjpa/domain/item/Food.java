package com.example.kdtjpa.domain.item;

import lombok.NoArgsConstructor;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Food extends Item {
    private String chef;

    public Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }
}
