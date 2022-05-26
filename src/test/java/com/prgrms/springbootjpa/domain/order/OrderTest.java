package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFiledException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class OrderTest {
    @Test
    @DisplayName("uuid를 입력하지 않을 경우 발생하는 예외 테스트")
    void testUuidValidation() {
        try {
            Order order = new Order(null, OrderStatus.OPENED, LocalDateTime.now());
        }catch(WrongFiledException e) {
            assertThat(e.getReason(), is("uuid is required field"));
        }
    }

    @Test
    @DisplayName("orderStatus를 입력하지 않을 경우 발생하는 예외 테스트")
    void testOrderStatusValidation() {
        try {
            Order order = new Order(UUID.randomUUID().toString(), null, LocalDateTime.now());
        }catch(WrongFiledException e) {
            assertThat(e.getReason(), is("orderStatus is required field"));
        }
    }

    @Test
    @DisplayName("createdAt를 입력하지 않을 경우 발생하는 예외 테스트")
    void testCreatedAtValidation() {
        try {
            Order order = new Order(UUID.randomUUID().toString(), OrderStatus.OPENED, null);
        }catch(WrongFiledException e) {
            assertThat(e.getReason(), is("createdAt is required field"));
        }
    }
}