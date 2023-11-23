package com.example.kdtjpa.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {
    private int width;
    private int height;

    @Builder
    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }
}
