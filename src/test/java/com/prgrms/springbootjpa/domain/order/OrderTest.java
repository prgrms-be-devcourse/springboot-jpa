package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {
    @Test
    @DisplayName("uuid를 입력하지 않을 경우 발생하는 예외 테스트")
    void testUuidValidation() {
        WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
            Order order = new Order(null, OrderStatus.OPENED, LocalDateTime.now());
        });
        assertThat(e.getReason(), is("uuid is required field"));
    }

    @Test
    @DisplayName("orderStatus를 입력하지 않을 경우 발생하는 예외 테스트")
    void testOrderStatusValidation() {
        WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
            Order order = new Order(UUID.randomUUID().toString(), null, LocalDateTime.now());
        });
        assertThat(e.getReason(), is("orderStatus is required field"));
    }

    @Test
    @DisplayName("createdAt를 입력하지 않을 경우 발생하는 예외 테스트")
    void testCreatedAtValidation() {
        WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
            Order order = new Order(UUID.randomUUID().toString(), OrderStatus.OPENED, null);
        });
        assertThat(e.getReason(), is("createdAt is required field"));
    }
}