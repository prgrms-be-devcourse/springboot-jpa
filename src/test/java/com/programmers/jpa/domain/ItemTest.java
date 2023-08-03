package com.programmers.jpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Nested
    @DisplayName("중첩: item 생성")
    class NewItemTest {
        @Test
        @DisplayName("성공")
        void newItem() {
            //given
            String name = "name";
            int price = 0;
            int stockQuantity = 0;

            //when
            Item item = new Item(name, price, stockQuantity);

            //then
            assertThat(item.getName()).isEqualTo(name);
            assertThat(item.getPrice()).isEqualTo(price);
            assertThat(item.getStockQuantity()).isEqualTo(stockQuantity);
        }


        @Test
        @DisplayName("예외: item name null")
        void newItem_ButNullName() {
            //given
            String nullName = null;

            //when
            //then
            assertThatThrownBy(() -> new Item(nullName, 1000, 1000))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("예외: 잘못된 범위의 name")
        void newItem_ButNameOutOfRange() {
            //given
            String nameOutOfRange = "a".repeat(201);

            //when
            //then
            assertThatThrownBy(() -> new Item(nameOutOfRange, 1000, 1000))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "-3", "-2", "-1"
        })
        @DisplayName("예외: 잘못된 범위의 price")
        void newItem_ButPriceOutOfRange(String priceOutOfRange) {
            assertThatThrownBy(() -> new Item("name", Integer.parseInt(priceOutOfRange), 1000))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "-3", "-2", "-1"
        })
        @DisplayName("예외: 잘못된 범위의 stockQuantity")
        void newItem_ButStockQuantityOutOfRange(String stockQuantityOutOfRange) {
            assertThatThrownBy(() -> new Item("name", 1000, Integer.parseInt(stockQuantityOutOfRange)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("예외: decreaseStockQuantity 호출 - 0 보다 작은 stockQuantity")
    void decreaseStockQuantity_ButStockQuantityLessThanZero() {
        //given
        Item item = new Item("name", 1000, 0);

        //when
        //then
        assertThatThrownBy(item::decreaseStockQuantity)
                .isInstanceOf(IllegalArgumentException.class);
    }
}