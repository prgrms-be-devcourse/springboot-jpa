package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemTest {
    @Test
    @DisplayName("잘못된 pirce 입력 범위에 대해 정상적으로 예외를 발생하는지에 대한 테스트")
    void testPriceValidation() {
        WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
            OrderItem orderItem = new OrderItem(1, OrderStatus.OPENED);
        });
        assertThat(e.getFieldName(), is("OrderItem.price"));
        assertThat(e.getReason(), Matchers.is("100 <= price"));
    }

    @Test
    @DisplayName("orderStatus를 입력하지 않을 경우 발생하는 예외 테스트")
    void testOrderStatusValidation() {
        WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
            OrderItem orderItem = new OrderItem(100, null);
        });
        assertThat(e.getFieldName(), is("OrderItem.orderStatus"));
        assertThat(e.getReason(), is("orderStatus is required field"));
    }
}