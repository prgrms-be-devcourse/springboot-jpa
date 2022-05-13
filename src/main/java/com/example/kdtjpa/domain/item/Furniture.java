package com.example.kdtjpa.domain.item;

import lombok.NoArgsConstructor;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("FURNITURE")
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Furniture extends Item {
    private int width;
    private int height;

    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }
}
