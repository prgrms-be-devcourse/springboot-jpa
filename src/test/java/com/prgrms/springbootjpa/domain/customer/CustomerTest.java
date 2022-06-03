package com.prgrms.springbootjpa.domain.customer;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {
    @Nested
    @DisplayName("엔티티 validation 테스트")
    class CustomerFieldValidationTest {
        @Nested
        @DisplayName("Customer 엔티티 firstName 필드 validation 테스트")
        class FistNameValidation {
            @Test
            @DisplayName("Customer 엔티티 firstName 필드 hastText validation 테스트")
            void testFirstNameHasText() {
                String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                    Customer customer = new Customer(null, "hong");
                }).getReason();
                assertThat(exceptionReason, is("please input firstName"));
            }

            @Test
            @DisplayName("Customer 엔티티 firstName 필드 length validation 테스트")
            void testFirstNameLength() {
                String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                    Customer customer = new Customer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "hong");
                }).getReason();
                assertThat(exceptionReason, is("1 <= firstName <= 30"));
            }
        }

        @Nested
        @DisplayName("Customer 엔티티 lastName 필드 validation 테스트")
        class LastNameValidation {
            @Test
            @DisplayName("Customer 엔티티 lastName 필드 hastText validation 테스트")
            void testFirstNameHasText() {
                String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                    Customer customer = new Customer("jerry", null);
                }).getReason();
                assertThat(exceptionReason, is("please input lastName"));
            }

            @Test
            @DisplayName("Customer 엔티티 lastName 필드 length validation 테스트")
            void testFirstNameLength() {
                String exceptionReason = assertThrows(WrongFieldException.class, () -> {
                    Customer customer = new Customer("jerry", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }).getReason();
                assertThat(exceptionReason, is("1 <= lastName <= 30"));
            }
        }
    }
}