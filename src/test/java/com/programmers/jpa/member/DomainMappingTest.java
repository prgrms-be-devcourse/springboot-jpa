package com.programmers.jpa.member;

import com.programmers.jpa.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DomainMappingTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("오더를 저장하면 오더와 연관관계를 맺고있는 OrderItem과 Item이 저장된다.")
    @Transactional
    void testMappingOrder() {
        Order order = createOrder();
        OrderItem orderItem = order.getOrderItems().get(0);
        Item item = orderItem.getItems().get(0);
        orderRepository.save(order);

        Order retrieveOrder = orderRepository.findById(1L).get();
        OrderItem retrieveOrderItem = retrieveOrder.getOrderItems().get(0);
        Item retrieveItem = retrieveOrderItem.getItems().get(0);

        assertThat(retrieveOrderItem).isEqualTo(orderItem);
        assertThat(retrieveItem).isEqualTo(item);
    }

    Order createOrder() {
        Order order = new Order(OrderStatus.OPENED, "부재시 문앞 보관 부탁드려요");
        OrderItem orderItem = new OrderItem(10000, 2);
        Item item = new Item("옷", 10000, 1000);
        orderItem.addItem(item);
        order.addOrderItem(orderItem);
        return order;
    }
}
