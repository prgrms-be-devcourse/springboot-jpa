package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.customer.Customer;
import com.example.springbootjpa.domain.item.Item;
import com.example.springbootjpa.domain.item.Keyboard;
import com.example.springbootjpa.domain.item.Mouse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        int quantity1 = 3;
        int quantity2 = 2;

        OrderItem orderItem1 = OrderItem.create(item1, item1.getPrice(), quantity1);
        OrderItem orderItem2 = OrderItem.create(item2, item2.getPrice(), quantity2);
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        //when
        Order order = Order.createOrder(customer, orderItems);

        //then
        assertThat(order.getCustomer()).isEqualTo(customer);
        assertThat(order.getOrderItems()).hasSize(2);
        assertThat(order.getOrderItems().get(0)).isEqualTo(orderItem1);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertNotNull(order.getOrderDate());
    }
}