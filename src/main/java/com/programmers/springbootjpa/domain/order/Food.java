package com.programmers.springbootjpa.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {

    private String chef;

    public Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);

        checkChef(chef);
        this.chef = chef;
    }

    private void checkChef(String chef) {
        checkLength(chef);
        checkCharacterPattern(chef);
    }

    private void checkLength(String request) {
        if (request.isEmpty() || request.length() > 20) {
            throw new IllegalArgumentException("1자 이상 20자 이하로 입력해주세요.");
        }
    }

    private void checkCharacterPattern(String request) {
        if (!Pattern.matches("^[가-힣a-zA-Z]+$", request)) {
            throw new IllegalArgumentException("한글 또는 영어로 입력해주세요.");
        }
    }

    public void updateChef(String chef) {
        checkChef(chef);
        this.chef = chef;
    }

    public String getChef() {
        return chef;
    }
}