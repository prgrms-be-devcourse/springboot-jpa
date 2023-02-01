package com.ys.jpa.domain.order.item;

import static org.junit.jupiter.api.Assertions.*;

import com.ys.jpa.domain.order.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ItemTest {


    @DisplayName("Item 생성 실패 테스트 - price 가 0보다 작으면 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3, -4, -5, -6})
    void createFailPriceMin(int price) {
        //given
        int quantity = 100;

        //when & then
        assertThrows(IllegalArgumentException.class,
            () -> new Item(price, quantity) {});
    }
    @DisplayName("Item 생성 실패 테스트 - quantity 가 0보다 작으면 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -3, -4, -5, -6})
    void createFailQuantityMin(int quantity) {
        //given
        int price = 10000;

        //when & then
        assertThrows(IllegalArgumentException.class,
            () -> new Item(price, quantity) {});
    }

    @DisplayName("changeOrderItem - 연관관계 편의 메서드 테스트  ")
    @Test
    void changeOrderItem() {
        //given
        OrderItem orderItem = OrderItem.create(10, 5);
        Item item = new Car(50, 10, 100);
        //when
        item.changeOrderItem(orderItem);

        //then
        assertTrue(orderItem.getItems().contains(item));
        assertEquals(orderItem, item.getOrderItem());
    }

}