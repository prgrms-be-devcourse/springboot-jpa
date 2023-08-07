package com.programmers.springbootjpa.domain.mission3.order;

import com.programmers.springbootjpa.domain.mission3.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        Member member = new Member("hyemin", "nick", 26, "서울특별시 성북구");

        order = new Order(OrderStatus.OPENED, "orderMemo", member);
    }

    @DisplayName("주문을 저장한다")
    @Test
    void testSave() {
        //given
        //when
        Order savedOrder = orderRepository.save(order);
        Order result = orderRepository.findById(savedOrder.getId()).get();

        //then
        assertThat(result.getOrderStatus()).isEqualTo(order.getOrderStatus());
        assertThat(result.getMemo()).isEqualTo(order.getMemo());
        assertThat(result.getMember()).isEqualTo(order.getMember());
    }

    @DisplayName("주문을 수정한다")
    @Test
    void testUpdate() {
        //given
        Order savedOrder = orderRepository.save(order);

        Member member = new Member("min", "nicky", 20, "경기도");

        //when
        savedOrder.update(OrderStatus.CANCELLED, "updateMemo", member);

        //then
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(savedOrder.getMemo()).isEqualTo("updateMemo");
        assertThat(savedOrder.getMember()).isEqualTo(member);
    }

    @DisplayName("주문을 id로 조회한다")
    @Test
    void testFindById() {
        //given
        Order savedOrder = orderRepository.save(order);

        //when
        Order result = orderRepository.findById(savedOrder.getId()).get();

        //then
        assertThat(result.getOrderStatus()).isEqualTo(savedOrder.getOrderStatus());
        assertThat(result.getMember()).isEqualTo(savedOrder.getMember());
    }

    @DisplayName("저장된 주문을 모두 조회한다")
    @Test
    void testFindAll() {
        //given
        orderRepository.save(order);

        Member member = new Member("min", "nicky", 20, "경기도");

        Order order2 = new Order(OrderStatus.OPENED, "orderMemo2", member);
        orderRepository.save(order2);

        //when
        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).hasSize(2);
    }

    @DisplayName("주문을 삭제한다")
    @Test
    void testDelete() {
        //given
        Order savedOrder = orderRepository.save(order);

        //when
        orderRepository.delete(savedOrder);
        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).isEmpty();
    }

    @DisplayName("저장된 주문을 모두 삭제한다")
    @Test
    void testDeleteAll() {
        //given
        orderRepository.save(order);

        Member member = new Member("min", "nicky", 20, "경기도");

        Order order2 = new Order(OrderStatus.OPENED, "orderMemo2", member);
        orderRepository.save(order2);

        //when
        orderRepository.deleteAll();
        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).isEmpty();
    }
}