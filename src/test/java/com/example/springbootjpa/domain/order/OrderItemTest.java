package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.item.Item;
import com.example.springbootjpa.domain.item.Mouse;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    @DisplayName("생성 실패 - orderPrice가 0보다 작다.")
    void createOrderItemFailTest_orderPrice(int invalidOrderPrice) throws Exception {

        //given
        int count = 5;

        //when -> then
        assertThrows(InvalidDomainConditionException.class,
                () -> new OrderItem(invalidOrderPrice, count));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    @DisplayName("생성 실패 - count가 0보다 작다.")
    void createOrderItemFailTest_count(int invalidCount) throws Exception {

        //given
        int orderPrice = 500;

        //when -> then
        assertThrows(InvalidDomainConditionException.class,
                () -> new OrderItem(orderPrice, invalidCount));
    }

    @Test
    @DisplayName("OrderItem 생성 성공")
    void createOrderItemTest() throws Exception {

        //given
        int initialStockQuantity = 10;
        int orderPrice = 1000;
        int quantity = 3;
        Item item = new Mouse(100, 10, "red");

        //when
        OrderItem orderItem = OrderItem.create(item, orderPrice, quantity);

        //then
        assertThat(orderItem.getOrderPrice()).isEqualTo(orderPrice);
        assertThat(orderItem.getQuantity()).isEqualTo(quantity);
        assertThat(orderItem.getItem()).isEqualTo(item);
        assertThat(item.getStockQuantity()).isEqualTo(initialStockQuantity - quantity);
    }
}