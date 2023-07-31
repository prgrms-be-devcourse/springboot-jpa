package com.programmers.springbootjpa.domain.mission3.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FurnitureTest {

    @DisplayName("가구를 생성한다")
    @CsvSource(value = {
            "10 : 20 : 30 : 40",
            "1 : 1 : 2 : 4",
            "100 : 33 : 55: 80"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(int price, int stockQuantity, int width, int height) {
        //given
        //when
        Furniture furniture = new Furniture(price, stockQuantity, width, height);

        //then
        assertThat(furniture.getPrice()).isEqualTo(price);
        assertThat(furniture.getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(furniture.getWidth()).isEqualTo(width);
        assertThat(furniture.getHeight()).isEqualTo(height);
    }

    @DisplayName("가구를 수정한다")
    @Test
    void testUpdate() {
        //given
        Furniture furniture = new Furniture(10, 20, 30, 40);

        //when
        furniture.updatePrice(20);
        furniture.updateStockQuantity(10);
        furniture.updateWidth(40);
        furniture.updateHeight(30);

        //then
        assertThat(furniture.getPrice()).isEqualTo(20);
        assertThat(furniture.getStockQuantity()).isEqualTo(10);
        assertThat(furniture.getWidth()).isEqualTo(40);
        assertThat(furniture.getHeight()).isEqualTo(30);
    }

    @DisplayName("가구 생성 시 금액이 0원보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testPrice(int price) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Furniture(price, 20, 30, 40))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("가구 생성 시 재고 수량이 0개보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testStockQuantity(int stockQuantity) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Furniture(10, stockQuantity, 30, 40))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가구 생성 시 너비 또는 높이가 1보다 작으면 예외처리한다")
    @CsvSource(value = {
            "30 : 0",
            "0 : 50",
            "55 : -1",
            "-7 : 60",
            "-4 : -8"
    }, delimiter = ':')
    @ParameterizedTest
    void testSize(int width, int height) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Furniture(10, 20, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가구 수정 시 금액이 0원보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testPriceUpdate(int price) {
        //given
        Furniture furniture = new Furniture(10, 20, 30, 40);

        //when
        //then
        assertThatThrownBy(() -> furniture.updatePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가구 수정 시 재고 수량이 0개보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testStockQuantityUpdate(int stockQuantity) {
        //given
        Furniture furniture = new Furniture(10, 20, 30, 40);

        //when
        //then
        assertThatThrownBy(() -> furniture.updateStockQuantity(stockQuantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가구 수정 시 너비가 1보다 작으면 예외처리한다")
    @ValueSource(strings = {"0", "-1", "-4", "-9"})
    @ParameterizedTest
    void testWidthUpdate(int width) {
        //given
        Furniture furniture = new Furniture(10, 20, 30, 40);

        //when
        //then
        assertThatThrownBy(() -> furniture.updateWidth(width))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가구 수정 시 높이가 1보다 작으면 예외처리한다")
    @ValueSource(strings = {"0", "-1", "-4", "-9"})
    @ParameterizedTest
    void testHeightUpdate(int height) {
        //given
        Furniture furniture = new Furniture(10, 20, 30, 40);

        //when
        //then
        assertThatThrownBy(() -> furniture.updateHeight(height))
                .isInstanceOf(IllegalArgumentException.class);
    }
}