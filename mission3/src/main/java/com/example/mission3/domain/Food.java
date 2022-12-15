package com.example.mission3.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;

    public Food(String name, long price, int stock) {
        super(name, price, stock);
    }

    public Food(String name, long price, int stock, String chef) {
        super(name, price, stock);
        this.chef = chef;
    }

}
