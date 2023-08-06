package com.programmers.jpa.order.domain;

import com.programmers.jpa.order.exception.OrderStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @DisplayName("주문 상태를 배송중으로 바꿀 수 있다.")
    @Test
    void changeOrderStatusToDelivering() {
        //given
        Order order = new Order("주문");

        //when
        order.changeOrderStatusToDelivering();

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @DisplayName("주문 상태가 성공이 아니면, 배송중으로 바꿀 수 없다.")
    @Test
    void throwExceptionWhenOrderStatusIsNotSuccess() {
        //given
        Order order1 = new Order("주문");
        ReflectionTestUtils.setField(order1, "orderStatus", OrderStatus.DELIVERING);

        Order order2 = new Order("주문");
        ReflectionTestUtils.setField(order2, "orderStatus", OrderStatus.DELIVERY_COMPLETE);

        //when, then
        assertThatThrownBy(() -> order1.changeOrderStatusToDelivering())
                .isInstanceOf(OrderStatusException.class);
        assertThatThrownBy(() -> order2.changeOrderStatusToDelivering())
                .isInstanceOf(OrderStatusException.class);
    }

    @DisplayName("주문 상태를 배송완료로 바꿀 수 있다.")
    @Test
    void changeOrderStatusToDeliveryComplete() {
        //given
        Order order = new Order("주문");
        ReflectionTestUtils.setField(order, "orderStatus", OrderStatus.DELIVERING);

        //when
        order.changeOrderStatusToDeliveryComplete();

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.DELIVERY_COMPLETE);
    }

    @DisplayName("주문 상태가 배송중이 아니면, 배송완료로 바꿀 수 없다.")
    @Test
    void throwExceptionWhenOrderStatusIsNotDelivering() {
        //given
        Order order1 = new Order("주문");

        Order order2 = new Order("주문");
        ReflectionTestUtils.setField(order2, "orderStatus", OrderStatus.DELIVERY_COMPLETE);

        //when, then
        assertThatThrownBy(() -> order1.changeOrderStatusToDeliveryComplete())
                .isInstanceOf(OrderStatusException.class);
        assertThatThrownBy(() -> order2.changeOrderStatusToDeliveryComplete())
                .isInstanceOf(OrderStatusException.class);
    }
}
