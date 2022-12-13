package com.ys.jpa.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ys.jpa.domain.order.Order;
import com.ys.jpa.domain.order.OrderItem;
import com.ys.jpa.domain.order.OrderRepository;
import com.ys.jpa.domain.order.item.Food;
import com.ys.jpa.domain.order.item.Item;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MappingTest {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("Order를 저장하면, OrderItem과 Item도 같이 저장된다.")
    @Test
    void createOrder() {
        //given
        Order order = Order.createDefaultMessage();
        OrderItem orderItem = OrderItem.create(5000, 5);
        Item item = new Food(1000, 5, "chef");

        orderItem.addItem(item);
        order.addOrderItem(orderItem);
        //when
        orderRepository.save(order);
        //then

        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        assertTrue(orderOptional.isPresent());
        Order findOrder = orderOptional.get();
        assertTrue(findOrder.getOrderItems().contains(orderItem));

        Optional<OrderItem> orderItemOptional = findOrder.getOrderItems()
            .stream()
            .filter(oi -> oi.getItems().contains(item))
            .findFirst();

        assertTrue(orderItemOptional.isPresent());
        OrderItem findOrderItem = orderItemOptional.get();
        assertEquals(orderItem, findOrderItem);
        assertTrue(findOrderItem.getItems().contains(item));
    }

}
