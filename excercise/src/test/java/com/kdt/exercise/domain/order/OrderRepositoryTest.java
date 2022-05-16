package com.kdt.exercise.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository repository;

    Order order;
    String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("메모");
        repository.save(order);
    }

    @Test
    void order_저장() {
        Assertions.assertThat(repository.findById(uuid)).isNotNull();
    }

    @Test
    void order_수정() {
        Order savedOrder = repository.findById(uuid).get();
        savedOrder.setMemo("메모수정");
        repository.save(savedOrder);
        Assertions.assertThat(repository.findById(uuid).get().getMemo()).isEqualTo("메모수정");
    }

    @Test
    void order_삭제() {
        Assertions.assertThat(repository.findById(uuid)).isPresent();
    }
}