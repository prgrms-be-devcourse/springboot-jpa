package com.example.mission3.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;

    public Food(String name, long price) {
        super(name,price);
    }
    public Food(String name, long price, int stock) {
        super(name,price,stock);
    }

}
