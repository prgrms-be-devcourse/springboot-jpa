package com.prgrms.m3.repository;

import com.prgrms.m3.domain.Car;
import com.prgrms.m3.domain.Member;
import com.prgrms.m3.domain.Order;
import com.prgrms.m3.domain.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    private Member member;
    private Car car;
    private OrderItem orderItem;

    private static final int STOCK_QUANTITY = 5;
    private static final int ORDER_QUANTITY = 3;

    @BeforeEach
    void setup() {
        member = memberRepository.save(new Member("kiseo", "k", 26, "카타르"));
        car = itemRepository.save(new Car(100, 20000, STOCK_QUANTITY));
        orderItem = OrderItem.createOrderItem(car, ORDER_QUANTITY);
    }


    @Test
    @DisplayName("Order가 성공적으로 저장된다.")
    void 저장() {
        Order order = Order.createOrder(member, orderItem);
        Order savedOrder = orderRepository.save(order);

        Optional<Order> findResult = orderRepository.findById(savedOrder.getId());
        assertTrue(findResult.isPresent());

        Order findOrder = findResult.get();

        assertEquals(order, findOrder);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Order 조회에 성공한다.")
    void 조회() {
        Order order = Order.createOrder(member, orderItem);
        Order savedOrder = orderRepository.save(order);

        Optional<Order> findResult = orderRepository.findById(savedOrder.getId());
        assertTrue(findResult.isPresent());

        Order findOrder = findResult.get();

        List<OrderItem> orderItems = findOrder.getOrderItems();

        assertThat(orderItems.get(0).getItem()).isInstanceOf(Car.class);
        assertThat(orderItems.get(0).getTotalPrice()).isEqualTo(60000);
    }

    @Test
    @DisplayName("Order 삭제에 성공한다.")
    void 삭제() {
        Order order = Order.createOrder(member, orderItem);
        Order savedOrder = orderRepository.save(order);

        Optional<Order> findResult = orderRepository.findById(savedOrder.getId());
        assertTrue(findResult.isPresent());

        Order findOrder = findResult.get();

        orderRepository.delete(findOrder);

        Optional<Order> afterDeleteResult = orderRepository.findById(savedOrder.getId());
        assertTrue(afterDeleteResult.isEmpty());
    }

    @Test
    @DisplayName("Order 수정에 성공한다.")
    void 수정() {
        Order order = Order.createOrder(member, orderItem);
        Order savedOrder = orderRepository.save(order);

        Optional<Order> findResult = orderRepository.findById(savedOrder.getId());
        assertTrue(findResult.isPresent());

        Order findOrder = findResult.get();
        findOrder.getOrderItems().forEach(orderItem -> orderItem.increaseQuantity(1));

        Order updatedOrder = orderRepository.findById(savedOrder.getId()).get();
        assertEquals(STOCK_QUANTITY - 1, updatedOrder.getOrderItems().get(0).getQuantity());
    }
}