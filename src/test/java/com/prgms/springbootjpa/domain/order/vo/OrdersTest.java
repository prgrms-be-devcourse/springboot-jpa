package com.prgms.springbootjpa.domain.order.vo;


import static org.assertj.core.api.Assertions.assertThat;

import com.prgms.springbootjpa.domain.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrdersTest {

    @Test
    @DisplayName("주문 추가")
    void testAddOrder() {
        //given
        var orders = new Orders();

        //when
        orders.add(new Order("---"));

        //then
        assertThat(orders.getOrders()).hasSize(1);
    }

    @Test
    @DisplayName("주문 삭제")
    void testRemoveOrder() {
        //given
        var orders = new Orders();
        var order = new Order("---");
        orders.add(order);

        //when
        orders.remove(order);

        //then
        assertThat(orders.getOrders()).isEmpty();
    }

}