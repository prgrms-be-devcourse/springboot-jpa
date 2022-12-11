package com.example.springbootpart4.domain;

import com.example.springbootpart4.domain.order.Order;
import com.example.springbootpart4.domain.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
public class JunitTest {

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        log.info("START");
    }

    @AfterEach
    void tearDown() {
        log.info("END");
    }

    @Test
    void name() {
        log.info("test");
    }

    @Test
    void springTest() {
        List<Order> all = orderRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }
}
