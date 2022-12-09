package com.example.mission1.repository;

import com.example.mission1.domain.Order;
import com.example.mission1.domain.OrderId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void createAndRead() {
        var newOrder = new Order();
        newOrder.setId(new OrderId("youngji@naver", "우리집"));

        orderRepository.save(newOrder);

        var findOrder = orderRepository.findById(new OrderId("youngji@naver", "우리집"));
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(findOrder.get().getCratedAt()).isEqualTo(newOrder.getCratedAt());

    }

}