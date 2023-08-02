package com.programmers.jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {
    Member givenMember;

    @BeforeEach
    void setUp() {
        givenMember = new Member("name", "name");
    }

    @Nested
    @DisplayName("중첩: order 생성")
    class NewOrderTest {
        @Test
        @DisplayName("성공")
        void newOrder() {
            //given
            String memo = "memo";

            //when
            Order order = new Order(memo, givenMember);

            //then
            assertThat(order.getMemo()).isEqualTo(memo);
            assertThat(order.getMember()).isEqualTo(givenMember);
        }

        @Test
        @DisplayName("예외: null로 주어진 member")
        void newOrder_ButNullMember() {
            //given
            Member nullMember = null;

            //when
            //then
            assertThatThrownBy(() -> new Order("member", nullMember))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("성공: cancelOrder 호출")
    void cancelOrder() {
        //given
        Order order = new Order("memo", givenMember);
        Item item = new Item("name", 1000, 1000);
        OrderItem orderItemA = new OrderItem(1000, order, item);
        OrderItem orderItemB = new OrderItem(1000, order, item);

        //when
        order.cancelOrder();

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(order.getOrderItems()).allSatisfy(orderItem -> {
            assertThat(orderItem.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
        });
    }
}