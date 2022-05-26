package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFiledException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ItemTest {
    @Nested
    class ItemFieldValidationTest {
        @Nested
        class NameValidation {
            @Test
            @DisplayName("잚못된 name 필드 입력에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNameHasText() {
                try {
                    Item item = new Item(null, 100, 0);
                }catch (WrongFiledException e) {
                    assertThat(e.getReason(), is("please input name"));
                }
            }

            @Test
            @DisplayName("잚못된 name 필드 길이에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNameLength() {
                try {
                    Item item = new Item("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 100, 0);
                }catch (WrongFiledException e) {
                    assertThat(e.getReason(), is("1 <= name <= 30"));
                }
            }
        }

        @Test
        @DisplayName("잚못된 pirce 입력 범위에 대해 정상적으로 예외를 발생하는지에 대한 테스트")
        void testPriceValidation() {
            try {
                Item item = new Item("a", 0, 0);
            }catch(WrongFiledException e) {
                assertThat(e.getReason(), is("100 <= price"));
            }
        }

        @Test
        @DisplayName("잚못된 stockQuantity 입력 범위에 대해 정상적으로 예외를 발생하는지에 대한 테스트")
        void testStockQuantityValidation() {
            try {
                Item item = new Item("a", 100, -1);
            }catch(WrongFiledException e) {
                assertThat(e.getReason(), is("0 <= stockQuantity"));
            }
        }
    }


}