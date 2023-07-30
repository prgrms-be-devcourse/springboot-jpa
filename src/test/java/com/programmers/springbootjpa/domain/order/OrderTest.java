package com.programmers.springbootjpa.domain.order;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class OrderTest {

    private Member member;

    private List<OrderItem> orderItems;

    @BeforeEach
    public void setUp() {
        member = new Member("hyemin", "nick", 26, "서울특별시 성북구");

        Car car = new Car(10, 10, 12);
        OrderItem carOrderItem = new OrderItem(2, car);

        Food food = new Food(20, 20, "Jung");
        OrderItem foodOrderItem = new OrderItem(3, food);

        Furniture furniture = new Furniture(30, 15, 5, 5);
        OrderItem furnitureOrderItem = new OrderItem(5, furniture);

        orderItems = Arrays.asList(carOrderItem, foodOrderItem, furnitureOrderItem);
    }

    @DisplayName("주문을 생성한다")
    @CsvSource(value = {
            "OPENED : orderOpened",
            "CANCELLED : orderClosed"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(OrderStatus orderStatus, String memo) {
        //given
        //when
        Order order = new Order(orderStatus, memo, member, orderItems);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(orderStatus);
        assertThat(order.getMemo()).isEqualTo(memo);
        assertThat(order.getMember()).isEqualTo(member);
        assertThat(order.getOrderItems()).containsAll(orderItems);
    }

    @DisplayName("주문을 수정한다")
    @Test
    void testUpdate() {
        //given
        Order order = new Order(OrderStatus.OPENED, "memo", member, orderItems);
        Member member2 = new Member("Jae", "jh", 26, "서울특별시 성북구");

        Car car = new Car(20, 10, 40);
        OrderItem carOrderItem = new OrderItem(5, car);
        List<OrderItem> orderItems2 = Arrays.asList(carOrderItem);

        //when
        order.updateOrderStatus(OrderStatus.CANCELLED);
        order.updateMemo("newMemo");
        order.updateMember(member2);
        order.updateOrderItems(orderItems2);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.getMemo()).isEqualTo("newMemo");
        assertThat(order.getMember()).isEqualTo(member2);
        assertThat(order.getOrderItems()).containsAll(orderItems2);
    }
}