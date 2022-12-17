package com.example.springboot_jpa.domain.orderItem;

import com.example.springboot_jpa.domain.item.Item;
import com.example.springboot_jpa.domain.item.ItemRepository;
import com.example.springboot_jpa.domain.order.Order;
import com.example.springboot_jpa.domain.order.OrderRepository;
import com.example.springboot_jpa.domain.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrderItemTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Order order;
    private Item item;
    private OrderItem orderItem;

    @BeforeEach
    void setup() {
        order = new Order("부산", "010-0000-0000", OrderStatus.OPENED);
        item = new Item("마우스", 100000, 1);
        orderItem = new OrderItem(0);
    }

    @Test
    @DisplayName("orderItem 이 저장되었는지 확인")
    void test() {
        orderRepository.save(order);
        itemRepository.save(item);

        System.out.println(item.getOrderItems());

        orderItem.setOrder(order);
        orderItem.setItem(item);

        orderItemRepository.save(orderItem);

        List<OrderItem> all = orderItemRepository.findAll();

        all.forEach(orderItem1 -> {
            System.out.println("orderItem1.getId() : " + orderItem1.getId());
            System.out.println("orderItem1.getOrder().getId() : " + orderItem1.getOrder().getId());
            System.out.println("orderItem1.getItem().getId() : " + orderItem1.getItem().getId());
        });
    }
}