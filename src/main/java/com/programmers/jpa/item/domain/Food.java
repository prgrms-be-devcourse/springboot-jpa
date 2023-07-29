package com.programmers.jpa.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item{

    @Column(length = 5)
    private String chef;

    protected Food() {
    }

    private Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }

    public static Item of(int price, int stockQuantity, String chef) {
        validateChef(chef);
        return new Food(price, stockQuantity, chef);
    }

    private static void validateChef(String chef) {
        if (Objects.isNull(chef) || chef.isBlank()) {
            throw new IllegalArgumentException("요리사가 비어있습니다.");
        }
    }

    @Override
    public String getChef() {
        return  chef;
    }
}
