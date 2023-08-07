package com.programmers.springbootjpa.domain.mission3.item;

import com.programmers.springbootjpa.global.exception.InvalidRequestValueException;
import jakarta.persistence.Column;
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

    private static final int MINIMUM_SIZE_LIMIT = 1;

    @Column
    private int width;

    @Column
    private int height;

    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);

        checkFurnitureSize(width);
        checkFurnitureSize(height);

        this.width = width;
        this.height = height;
    }

    private void checkFurnitureSize(int request) {
        if (request < MINIMUM_SIZE_LIMIT) {
            throw new InvalidRequestValueException("사이즈는 1보다 작을 수 없습니다.");
        }
    }

    public void update(int price, int stockQuantity, int width, int height) {
        update(price, stockQuantity);

        checkFurnitureSize(width);
        this.width = width;

        checkFurnitureSize(height);
        this.height = height;
    }
}
