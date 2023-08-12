package com.programmers.jpa.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Food extends Item{

    public static final int MAX_CHEF_LENGTH = 5;

    @Column(length = MAX_CHEF_LENGTH)
    private String chef;

    private Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }

    public static Food of(int price, int stockQuantity, String chef) {
        validateChef(chef);
        return new Food(price, stockQuantity, chef);
    }

    private static void validateChef(String chef) {
        if (Objects.isNull(chef) || chef.isBlank()) {
            throw new IllegalArgumentException("요리사가 비어있습니다.");
        }

        if (chef.length() > MAX_CHEF_LENGTH) {
            throw new IllegalArgumentException(String.format("요리사 이름이 %s 글자수를 넘었습니다.", MAX_CHEF_LENGTH));
        }
    }
}
