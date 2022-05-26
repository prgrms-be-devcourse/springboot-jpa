package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFiledException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {
    @Test
    @DisplayName("잚못된 pirce 입력 범위에 대해 정상적으로 예외를 발생하는지에 대한 테스트")
    void testPriceValidation() {
        try {
            OrderItem orderItem = new OrderItem(1, OrderStatus.OPENED);
        }catch(WrongFiledException e) {
            assertThat(e.getFieldName(), is("OrderItem.price"));
            assertThat(e.getReason(), Matchers.is("100 <= price"));
        }
    }

    @Test
    @DisplayName("orderStatus를 입력하지 않을 경우 발생하는 예외 테스트")
    void testOrderStatusValidation() {
        try {
            OrderItem orderItem = new OrderItem(100, null);
        }catch(WrongFiledException e) {
            assertThat(e.getFieldName(), is("OrderItem.orderStatus"));
            assertThat(e.getReason(), is("orderStatus is required field"));
        }
    }
}