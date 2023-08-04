package com.programmers.week.item.domain;

import com.programmers.week.exception.Message;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {

    private static final int MAX_CHEF_LENGTH = 5;

    @Column(length = 5)
    private String chef;

    private Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }

    public static Item of(int price, int stockQuantity, String chef) {
        validateFood(chef);
        return new Food(price, stockQuantity, chef);
    }

    private static void validateFood(String chef) {
        if (Objects.isNull(chef) || chef.isBlank()) {
            throw new IllegalStateException(Message.CHEF_IS_NULL);
        }
        if (chef.length() > MAX_CHEF_LENGTH) {
            throw new IllegalArgumentException(String.format(Message.CHEF_LENGTH_IS_WRONG + "%s", chef));
        }
    }

}
