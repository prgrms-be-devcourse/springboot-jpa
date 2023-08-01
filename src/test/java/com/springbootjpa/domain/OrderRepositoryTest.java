package com.springbootjpa.domain;

import com.springbootjpa.global.NoSuchEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Order order;

    @BeforeEach
    void setup() {
        Customer customer = new Customer("이", "근우");
        customerRepository.save(customer);

        Delivery delivery = new Delivery("경기도 고양시", "한화오벨리스크");
        order = new Order(customer, delivery);
        Item item = new Item("아이폰24", 10);
        itemRepository.save(item);

        OrderItem orderItem = new OrderItem(order, item);
        orderItem.orderItem(2);
        order.addOrderItem(orderItem);
    }

    @Test
    void 주문을_생성한다() {
        // given & when
        Order result = orderRepository.save(order);

        // then
        assertThat(result.getDelivery()).isEqualTo(new Delivery("경기도 고양시", "한화오벨리스크"));
    }

    @Test
    void 주문을_조회한다() {
        // given
        Order savedOrder = orderRepository.save(order);

        // when
        Order result = orderRepository.getById(savedOrder.getId());

        // then
        assertThat(result.getId()).isEqualTo(savedOrder.getId());
    }

    @Test
    void 주문상품을_추가하여_주문을_변경한다() {
        // given
        Order savedOrder = orderRepository.save(order);
        Item item = new Item("갤럭시22", 10);
        itemRepository.save(item);

        OrderItem orderItem = new OrderItem(order, item);

        // when
        orderItem.orderItem(2);
        order.addOrderItem(orderItem);
        Order result = orderRepository.getById(savedOrder.getId());

        // then
        assertThat(result.getOrderItems()).hasSize(2);
    }

    @Test
    void 주문을_아이디로_삭제한다() {
        // given
        Order savedOrder = orderRepository.save(order);
        Long id = savedOrder.getId();

        // when
        orderRepository.deleteById(id);

        // then
        assertThatThrownBy(() -> orderRepository.getById(id))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessage("존재하지 않는 주문입니다.");
    }
}
