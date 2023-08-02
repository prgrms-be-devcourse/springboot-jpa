package com.programmers.jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemTest {
    Member givenMember;
    Order givenOrder;
    Item givenItem;

    @BeforeEach
    void setUp() {
        givenMember = new Member("name", "name");
        givenOrder = new Order("memo", givenMember);
        givenItem = new Item("name", 1000, 1000);
    }

    @Nested
    @DisplayName("중첩: orderItem 생성")
    class NewOrderItemTest {
        @Test
        @DisplayName("성공")
        void newOrderItem() {
            //given
            int stockQuantity = 1000;
            int price = 100;
            Item item = new Item("name", 1000, stockQuantity);

            //when
            OrderItem orderItem = new OrderItem(price, givenOrder, item);

            //then
            assertThat(orderItem.getPrice()).isEqualTo(price);
            assertThat(orderItem.getOrder()).isEqualTo(givenOrder);
            assertThat(orderItem.getItem()).isEqualTo(item);
            assertThat(item.getStockQuantity()).isEqualTo(stockQuantity - 1);
        }

        @ParameterizedTest
        @CsvSource({
                "-3", "-2", "-1"
        })
        @DisplayName("예외: 잘못된 범위의 price")
        void newOrderItem_ButPriceOutOfRange(String priceOutOfRange) {
            assertThatThrownBy(() -> new OrderItem(Integer.parseInt(priceOutOfRange), givenOrder, givenItem))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("성공: cancelOrderItem 호출")
    void cancelOrderItem() {
        //given
        int stockQuantity = 1000;
        Item item = new Item("name", 1000, stockQuantity);
        OrderItem orderItem = new OrderItem(1000, givenOrder, item);

        //when
        orderItem.cancelOrderItem();

        //then
        assertThat(orderItem.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(item.getStockQuantity()).isEqualTo(stockQuantity);
    }
}