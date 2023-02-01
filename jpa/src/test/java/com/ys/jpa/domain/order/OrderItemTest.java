package com.ys.jpa.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import com.ys.jpa.domain.member.Member;
import com.ys.jpa.domain.order.item.Car;
import com.ys.jpa.domain.order.item.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderItemTest {

    @DisplayName("OrderItem 생성 실패 테스트 - price가 0보다 작으면 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3, -4, -5, -6})
    void createFailPriceMin(int price) {
        //given
        int quantity = 10;
        //when & then
        assertThrows(IllegalArgumentException.class,
            () -> new OrderItem(price, quantity));
    }

    @DisplayName("OrderItem 생성 실패 테스트 - quantity가 0보다 작으면 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3, -4, -5, -6})
    void createFailQuantityMin(int quantity) {
        //given
        int price = 9000;
        //when & then
        assertThrows(IllegalArgumentException.class,
            () -> new OrderItem(price, quantity));
    }

    @DisplayName("changeOrder - 연관관계 편의 메서드 테스트  ")
    @Test
    void changeMember() {
        //given
        Order order = Order.createDefaultMessage();
        OrderItem orderItem = OrderItem.create(10, 5);
        //when
        orderItem.changeOrder(order);

        //then
        assertTrue(order.getOrderItems().contains(orderItem));
        assertEquals(order, orderItem.getOrder());
    }

    @DisplayName("addItem - 연관관계 편의 메서드 테스트  ")
    @Test
    void addItem() {
        //given
        OrderItem orderItem = OrderItem.create(10, 5);
        Item item = new Car(50, 10, 100);
        //when
        orderItem.addItem(item);

        //then
        assertTrue(orderItem.getItems().contains(item));
        assertEquals(orderItem, item.getOrderItem());
    }
    
}