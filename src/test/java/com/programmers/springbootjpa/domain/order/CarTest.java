package com.programmers.springbootjpa.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CarTest {

    @DisplayName("자동차를 생성한다")
    @CsvSource(value = {
            "10 : 20 : 300",
            "1 : 1 : 1",
            "100 : 33 : 1"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(int price, int stockQuantity, int power) {
        //given
        //when
        Car car = new Car(price, stockQuantity, power);

        //then
        assertThat(car.getPrice()).isEqualTo(price);
        assertThat(car.getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(car.getPower()).isEqualTo(power);
    }

    @DisplayName("자동차를 수정한다")
    @Test
    void testUpdate() {
        //given
        Car car = new Car(10, 20, 300);

        //when
        car.updatePrice(2);
        car.updateStockQuantity(3);
        car.updatePower(5);

        //then
        assertThat(car.getPrice()).isEqualTo(2);
        assertThat(car.getStockQuantity()).isEqualTo(3);
        assertThat(car.getPower()).isEqualTo(5);
    }

    @DisplayName("자동차 생성 시 금액이 0원보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testPrice(int price) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Car(price, 20, 30))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자동차 생성 시 재고 수량이 0개보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testStockQuantity(int stockQuantity) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Car(10, stockQuantity, 30))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자동차 생성 시 동력이 1보다 작으면 예외처리한다")
    @ValueSource(strings = {"0", "-1", "-4", "-9"})
    @ParameterizedTest
    void testPower(int power) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Car(10, 20, power))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자동차 수정 시 금액이 0원보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testPriceUpdate(int price) {
        //given
        Car car = new Car(10, 20, 300);

        //when
        //then
        assertThatThrownBy(() -> car.updatePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자동차 수정 시 재고 수량이 0개보다 적으면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testStockQuantityUpdate(int stockQuantity) {
        //given
        Car car = new Car(10, 20, 300);

        //when
        //then
        assertThatThrownBy(() -> car.updateStockQuantity(stockQuantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자동차 수정 시 동력이 1보다 작으면 예외처리한다")
    @ValueSource(strings = {"0", "-1", "-4", "-9"})
    @ParameterizedTest
    void testPowerUpdate(int power) {
        //given
        Car car = new Car(10, 20, 300);

        //when
        //then
        assertThatThrownBy(() -> car.updatePower(power))
                .isInstanceOf(IllegalArgumentException.class);
    }
}