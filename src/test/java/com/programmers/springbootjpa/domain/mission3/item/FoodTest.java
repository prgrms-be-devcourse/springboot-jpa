package com.programmers.springbootjpa.domain.mission3.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FoodTest {

    @DisplayName("음식을 생성한다")
    @CsvSource(value = {
            "10 : 20 : hyemin",
            "1 : 1 : jae",
            "100 : 33 : min"
    }, delimiter = ':')
    @ParameterizedTest
    void create(int price, int stockQuantity, String chef) {
        //given
        //when
        Food food = new Food(price, stockQuantity, chef);

        //then
        assertThat(food.getPrice()).isEqualTo(price);
        assertThat(food.getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(food.getChef()).isEqualTo(chef);
    }

    @DisplayName("음식을 수정한다")
    @Test
    void testUpdate() {
        //given
        Food food = new Food(10, 20, "hyemin");

        //when
        food.update(2, 3, "min");

        //then
        assertThat(food.getPrice()).isEqualTo(2);
        assertThat(food.getStockQuantity()).isEqualTo(3);
        assertThat(food.getChef()).isEqualTo("min");
    }

    @DisplayName("음식 생성 시 금액이 0원보다 작으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testPrice(int price) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Food(price, 20, "hyemin"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음식 생성 시 재고 수량이 0개보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testStockQuantity(int stockQuantity) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Food(10, stockQuantity, "hyemin"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음식 생성 시 chef가 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbb", "혜민1", "@@", "hy3min", "/bbb"})
    @NullAndEmptySource
    @ParameterizedTest
    void testChef(String chef) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Food(10, 20, chef))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("음식 수정 시 금액이 0원보다 작으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testPriceUpdate(int price) {
        //given
        Food food = new Food(10, 20, "hyemin");

        //when
        //then
        assertThatThrownBy(() -> food.update(price, 20, "hyemin"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음식 수정 시 재고 수량이 0개보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testStockQuantityUpdate(int stockQuantity) {
        //given
        Food food = new Food(10, 20, "hyemin");

        //when
        //then
        assertThatThrownBy(() -> food.update(10, stockQuantity, "hyemin"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음식 수정 시 chef가 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbb", "혜민1", "@@", "hy3min", "/bbb"})
    @NullAndEmptySource
    @ParameterizedTest
    void testChefUpdate(String chef) {
        //given
        Food food = new Food(10, 20, "hyemin");

        //when
        //then
        assertThatThrownBy(() -> food.update(10, 20, chef))
                .isInstanceOf(IllegalArgumentException.class);
    }
}