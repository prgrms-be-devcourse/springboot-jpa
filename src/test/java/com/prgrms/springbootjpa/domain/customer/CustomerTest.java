package com.prgrms.springbootjpa.domain.customer;

import com.prgrms.springbootjpa.global.exception.WrongFiledException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CustomerTest {
    @Nested
    @DisplayName("엔티티 validation 테스트")
    class TestEntityValidation {
        @Nested
        @DisplayName("Customer 엔티티 firstName 필드 validation 테스트")
        class FistNameValidation {
            @Test
            @DisplayName("Customer 엔티티 firstName 필드 hastText validation 테스트")
            public void testFirstNameHasText() {
                try {
                    Customer customer = new Customer(null, "hong");
                }catch (WrongFiledException e) {
                    assertThat(e.getReason(), is("please input firstName"));
                }
            }

            @Test
            @DisplayName("Customer 엔티티 firstName 필드 length validation 테스트")
            public void testFirstNameLength() {
                try {
                    Customer customer = new Customer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "hong");
                }catch (WrongFiledException e) {
                    assertThat(e.getReason(), is("1 <= firstName <= 30"));
                }
            }
        }

        @Nested
        @DisplayName("Customer 엔티티 lastName 필드 validation 테스트")
        class LastNameValidation {
            @Test
            @DisplayName("Customer 엔티티 lastName 필드 hastText validation 테스트")
            public void testFirstNameHasText() {
                try {
                    Customer customer = new Customer("jerry", null);
                }catch (WrongFiledException e) {
                    assertThat(e.getReason(), is("please input lastName"));
                }
            }

            @Test
            @DisplayName("Customer 엔티티 lastName 필드 length validation 테스트")
            public void testFirstNameLength() {
                try {
                    Customer customer = new Customer("jerry", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }catch (WrongFiledException e) {
                    assertThat(e.getReason(), is("1 <= lastName <= 30"));
                }
            }
        }
    }
}