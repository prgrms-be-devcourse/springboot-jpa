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
@DiscriminatorValue("DRINK")
public class Drink extends Item {
    private int amount;
    public Drink(String name, long price) {
        super(name,price);
    }
    public Drink(String name, long price, int stock) {
        super(name,price,stock);
    }
}
