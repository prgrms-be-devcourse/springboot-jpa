package com.example.kdtjpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void test() {
        String uuid = UUID.randomUUID().toString();
        Order order = Order.builder()
                .uuid(uuid)
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("---").build();
        order.setCreatedBy("eugene Park");

        orderRepository.save(order);

        Order order1 = orderRepository.findById(uuid).get();
        List<Order> all = orderRepository.findAll();

        orderRepository.findAllByOrderStatus(OrderStatus.OPENED);
        orderRepository.findAllByOrderStatusOrderByOrderDatetime(OrderStatus.OPENED);

        orderRepository.findOrder(order.getMemo());
    }
}