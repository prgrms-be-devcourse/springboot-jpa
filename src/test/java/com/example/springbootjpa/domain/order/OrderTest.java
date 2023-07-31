package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.customer.Customer;
import com.example.springbootjpa.domain.item.Item;
import com.example.springbootjpa.domain.item.Keyboard;
import com.example.springbootjpa.domain.item.Mouse;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    @DisplayName("Order 생성 테스트")
    void createOrderTest() throws Exception {

        //given
        Customer customer = Customer.builder()
                .id(1L)
                .username("hong")
                .address("부산시")
                .build();

        Item item1 = new Keyboard(1000, 10, "white");
        Item item2 = new Mouse(500, 5, "red");
        int item1InitialStockQuantity = 10;
        int item2InitialStockQuantity = 5;

        int requestQuantity1 = 3;
        int requestQuantity2 = 2;

        OrderItem orderItem1 = OrderItem.create(item1, item1.getPrice(), requestQuantity1);
        OrderItem orderItem2 = OrderItem.create(item2, item2.getPrice(), requestQuantity2);
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        //when
        Order order = Order.createOrder(customer, orderItems);
        int totalPrice = order.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(item1.getPrice() * requestQuantity1 + item2.getPrice() * requestQuantity2);
        assertThat(item1.getStockQuantity()).isEqualTo(item1InitialStockQuantity - requestQuantity1);
        assertThat(item2.getStockQuantity()).isEqualTo(item2InitialStockQuantity - requestQuantity2);
        assertThat(order.getCustomer()).isEqualTo(customer);
        assertThat(order.getOrderItems()).hasSize(2);
        assertThat(order.getOrderItems().get(0)).isEqualTo(orderItem1);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertNotNull(order.getOrderDate());
    }

    @Test
    @DisplayName("Order 생성 테스트 실패 - 주문 항목이 비어있는 경우")
    void createOrderFailTest_EmptyItems() {

        // given
        Customer customer = Customer.builder()
                .id(1L)
                .username("hong")
                .address("부산시")
                .build();

        List<OrderItem> orderItems = Collections.emptyList();

        // when -> then
        assertThrows(InvalidDomainConditionException.class,
                () -> Order.createOrder(customer, orderItems));
    }

    @Test
    @DisplayName("Order 생성 테스트 - 주문 항목이 null인 경우")
    void createOrderFailTest_NullItems() {

        // given
        Customer customer = Customer.builder()
                .id(1L)
                .username("hong")
                .address("부산시")
                .build();

        List<OrderItem> orderItems = null;

        // when -> then
        assertThrows(InvalidDomainConditionException.class,
                () -> Order.createOrder(customer, orderItems));
    }
}