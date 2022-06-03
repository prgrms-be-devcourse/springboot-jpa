package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {
    @Nested
    class ItemFieldValidationTest {
        @Nested
        class NameValidation {
            @Test
            @DisplayName("잘못된 name 필드 입력에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNameHasText() {
                String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                    Item item = new Item(null, 100, 0);
                }).getReason();
                assertThat(exceptionReason, is("please input name"));
            }

            @Test
            @DisplayName("잘못된 name 필드 길이에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNameLength() {
                String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                    Item item = new Item("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 100, 0);
                }).getReason();
                assertThat(exceptionReason, is("1 <= name <= 30"));
            }
        }

        @Test
        @DisplayName("잘못된 pirce 입력 범위에 대해 정상적으로 예외를 발생하는지에 대한 테스트")
        void testPriceValidation() {
            String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                Item item = new Item("a", 0, 0);
            }).getReason();
            assertThat(exceptionReason, is("100 <= price"));
        }

        @Test
        @DisplayName("잘못된 stockQuantity 입력 범위에 대해 정상적으로 예외를 발생하는지에 대한 테스트")
        void testStockQuantityValidation() {
            String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                Item item = new Item("a", 100, -1);
            }).getReason();
            assertThat(exceptionReason, is("0 <= stockQuantity"));
        }
    }
}