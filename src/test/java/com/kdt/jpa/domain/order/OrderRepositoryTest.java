package com.kdt.jpa.domain.order;

import com.kdt.jpa.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@DisplayName("Spring DATA JPA 테스트")
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    private Member member;
    private Order order;
    private String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setup() {
        order = new Order();
        order.setOrderId(uuid);
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("test");
        order.setCreatedBy("beomseok.ko");
        order.setCreatedAt(LocalDateTime.now());

        member = new Member();
        member.setName("beomseok");
        member.setNickname("beomsic");
        member.setAge(26);
        member.setAddress("서울시 구로구");
        member.setDescription("hi");

        order.setMember(member);

        orderRepository.save(order);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Spring data JPA 테스트")
    void testJPA() {
        // Given

        // When
        Order order1 = orderRepository.findById(uuid)
                .orElseThrow(RuntimeException::new);

        List<Order> all = orderRepository.findAll();

        // Then
        assertThat(orderRepository.existsById(uuid), is(true));
        assertThat(order1.getMemo().equals("test"), is(true));
        assertThat(all, hasSize(1));
    }

    @Test
    @DisplayName("메소드 쿼리 테스트")
    void testMethodQuery() {
        List<Order> orders = orderRepository.findAllByOrderStatus(OrderStatus.OPENED);
        assertThat(orders, hasSize(1));
        assertThat(orders.get(0).getMemo().equals("test"), is(true));

        orders = orderRepository.findAllByOrderStatusOrderByOrderDatetime(OrderStatus.OPENED);
        assertThat(orders, hasSize(1));

//        assertThat(orders.contains(order), is(true));
    }

    @Test
    @DisplayName("커스텀 쿼리 테스트")
    void testCustomQuery() {
        Order order1 = orderRepository.findByMemo("test")
                .orElseThrow(RuntimeException::new);
        assertThat(order1.getMemo().equals("test"), is(true));

    }
}