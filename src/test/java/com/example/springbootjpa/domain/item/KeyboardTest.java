package com.example.springbootjpa.domain.item;

import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeyboardTest {

    @Test
    @DisplayName("Keyboard 생성 성공")
    void createSuccessTest() throws Exception {

        //given
        int price = 1000;
        int quantity = 10;
        String color = "red";
        //when
        Keyboard keyboard = assertDoesNotThrow(() -> new Keyboard(price, quantity, color));

        //then
        assertThat(keyboard.getColor()).isEqualTo(color);
        assertThat(keyboard.getPrice()).isEqualTo(price);
        assertThat(keyboard.getStockQuantity()).isEqualTo(quantity);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    @DisplayName("KeyBoard 생성 실패 - 잘못된 color")
    void createFailTest(String invalidColor) throws Exception {

        //given
        int price = 1000;
        int quantity = 10;

        //when -> then
        assertThrows(InvalidDomainConditionException.class,
                () -> new Keyboard(price, quantity, invalidColor));
    }
}