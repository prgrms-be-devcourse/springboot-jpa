package com.programmers.springbootjpa.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {

    private int width;
    private int height;

    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);

        checkFurnitureSize(width);
        checkFurnitureSize(height);

        this.width = width;
        this.height = height;
    }

    private void checkFurnitureSize(int request) {
        if (request < 1) {
            throw new IllegalArgumentException("사이즈는 1보다 작을 수 없습니다.");
        }
    }

    public void updateWidth(int width) {
        checkFurnitureSize(width);
        this.width = width;
    }

    public void updateHeight(int height) {
        checkFurnitureSize(height);
        this.height = height;
    }
}
