package com.example.mission1.repository;

import com.example.mission1.domain.Order;
import com.example.mission1.domain.OrderId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("식별자 클래스를 id로 갖는 entity를 만들고 읽을 수 있다.")
    void createAndRead() {
        var newOrder = new Order(new OrderId("youngji@naver", "우리집"));

        orderRepository.save(newOrder);

        var findOrder = orderRepository.findById(new OrderId("youngji@naver", "우리집"));
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(findOrder.get().getCreatedAt()).isEqualTo(newOrder.getCreatedAt());
    }
}