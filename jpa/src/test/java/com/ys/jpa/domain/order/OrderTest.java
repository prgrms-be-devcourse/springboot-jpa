package com.ys.jpa.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import com.ys.jpa.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

    @DisplayName("오더 생성 테스트 - memo가 빈 값이면 default 메시지로 생성된다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void create(String memo) {
        //given & when
        Order order = Order.create(memo);
        // then
        assertEquals("부재시 전화주세요.", order.getMemo());
    }

    @DisplayName("오더 생성 테스트 - default 메시지로 생성된다")
    @Test
    void createDefaultMessage() {
        //given & when
        Order order = Order.createDefaultMessage();
        // then
        assertEquals("부재시 전화주세요.", order.getMemo());
    }

    @DisplayName("changeMember - 연관관계 편의 메서드 테스트  ")
    @Test
    void changeMember() {
        //given
        Member member = new Member("ys", "ysking", 28, "서울시 노원구");
        Order order = Order.createDefaultMessage();
        //when
        order.changeMember(member);

        //then
        assertTrue(member.getOrders().contains(order));
        assertEquals(order.getMember(), member);
    }


    @DisplayName("addOrderItem - 연관관계 편의 메서드 테스트  ")
    @Test
    void addOrderItem() {
        //given
        Order order = Order.createDefaultMessage();
        OrderItem orderItem = OrderItem.create(10, 5);
        //when
        order.addOrderItem(orderItem);

        //then
        assertTrue(order.getOrderItems().contains(orderItem));
        assertEquals(order, orderItem.getOrder());
    }
}