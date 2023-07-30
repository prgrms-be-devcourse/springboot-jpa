package com.programmers.springbootjpa.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class OrderItemTest {

    @DisplayName("주문상품을 생성한다")
    @CsvSource(value = {
            "2 : 10 : 30 : 3",
            "4 : 1 : 10 : 1",
            "100 : 33 : 55 : 20"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(int price, int stockQuantity, int power, int requestQuantity) {
        //given
        //when
        Car car = new Car(price, stockQuantity, power);
        OrderItem orderItem = new OrderItem(requestQuantity, car);

        //then
        assertThat(orderItem.getPrice()).isEqualTo(price);
        assertThat(orderItem.getQuantity()).isEqualTo(requestQuantity);
        assertThat(orderItem.getItem()).isEqualTo(car);
    }

    @DisplayName("주문상품을 수정한다")
    @Test
    void testUpdate() {
        //given
        Car car = new Car(2, 10, 30);
        OrderItem orderItem = new OrderItem(3, car);

        //when
        Car car2 = new Car(3, 20, 40);
        orderItem.updateItem(car2);

        int expectedQuantity = 11;
        orderItem.updateQuantity(expectedQuantity);

        //then
        assertThat(orderItem.getQuantity()).isEqualTo(expectedQuantity);
        assertThat(orderItem.getItem()).isEqualTo(car2);
    }

    @DisplayName("주문상품 생성 시 수량이 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"0", "-1", "-4", "-9", "11", "13", "20"})
    @ParameterizedTest
    void testQuantity(int quantity) {
        //given
        int carStockQuantity = 10;
        Car car = new Car(8, carStockQuantity, 30);

        //when
        //then
        assertThatThrownBy(() -> new OrderItem(quantity, car))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문상품 수정 시 금액이 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"0", "-1", "-4", "-9", "11", "13", "20"})
    @ParameterizedTest
    void testPriceUpdate(int price) {
        //given
        int carPrice = 8;
        Car car = new Car(carPrice, 10, 30);
        OrderItem orderItem = new OrderItem(5, car);

        //when
        //then
        assertThatThrownBy(() -> orderItem.updatePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문상품 수정 시 수량이 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"0", "-1", "-4", "-9", "11", "13", "20"})
    @ParameterizedTest
    void testQuantityUpdate(int quantity) {
        //given
        int carStockQuantity = 10;
        Car car = new Car(8, carStockQuantity, 30);
        OrderItem orderItem = new OrderItem(5, car);

        //when
        //then
        assertThatThrownBy(() -> orderItem.updateQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class);
    }
}