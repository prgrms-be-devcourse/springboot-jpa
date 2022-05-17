package com.prgms.springbootjpa.domain.order.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgms.springbootjpa.domain.order.OrderItem;
import com.prgms.springbootjpa.domain.order.item.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderItemsTest {

    private OrderItem orderItem1;
    private OrderItem orderItem2;

    @BeforeEach
    void setUp() {

        var car = new Car(new Price(2000), new Quantity(100), 100);

        orderItem1 = new OrderItem(new Price(2000), new Quantity(10), car);
        orderItem2 = new OrderItem(new Price(2000), new Quantity(20), car);
    }

    @Test
    @DisplayName("orderItem 추가")
    void testAddSuccess() {
        //given
        var orderItems = new OrderItems();

        //when
        orderItems.add(orderItem1);

        //then
        assertThat(orderItems.getOrderItems()).hasSize(1);
    }

    @Test
    @DisplayName("orderItem 제거")
    void testRemoveSuccess() {
        //given
        var orderItems = new OrderItems();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        //when
        orderItems.remove(orderItem1);

        //then
        assertAll(
            () -> assertThat(orderItems.getOrderItems().size()).isEqualTo(1),
            () -> assertThat(orderItems.getOrderItems().get(0)).isEqualTo(orderItem2)
        );
    }
}