package com.example.springjpamission.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("FOOD")
@Entity
public class Food extends Item {

    @Column
    private String chef;

    protected Food() { }

    public Food(Price price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        validateChef(chef);
        this.chef = chef;
    }

    private void validateChef(String chef) {
        if (chef == null || chef.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

}
