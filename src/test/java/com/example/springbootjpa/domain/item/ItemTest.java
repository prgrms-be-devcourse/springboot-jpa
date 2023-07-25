package com.example.springbootjpa.domain.item;

import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    @DisplayName("생성 실패 - price가 0보다 작다.")
    void createItemFailTest_price(int invalidPrice) throws Exception {

        //given
        int quantity = 5;

        //when -> then
        assertThrows(InvalidDomainConditionException.class,
                () -> new Item(invalidPrice, quantity) {
                });
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    @DisplayName("생성 실패 - quantity가 0보다 작다.")
    void createItemFailTest_quantity(int invalidQuantity) throws Exception {

        //given
        int price = 5;

        //when -> then
        assertThrows(InvalidDomainConditionException.class,
                () -> new Item(price, invalidQuantity) {
                });
    }
}