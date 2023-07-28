package org.prgms.springbootjpa.mission3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.mission3.domain.member.Member;
import org.prgms.springbootjpa.mission3.domain.member.MemberRepository;
import org.prgms.springbootjpa.mission3.domain.order.Order;
import org.prgms.springbootjpa.mission3.domain.order.OrderItem;
import org.prgms.springbootjpa.mission3.domain.order.OrderItemRepository;
import org.prgms.springbootjpa.mission3.domain.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.prgms.springbootjpa.mission3.domain.order.OrderStatus.CREATED;

@SpringBootTest
class OrderItemOrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;

    Member member = new Member("현지", "딩딩", 25, "서울시 마포구");
    Order order = new Order(CREATED, "집 앞 배송", member);
    OrderItem orderItem = new OrderItem(10000, 2, order);

    @BeforeEach
    void setUp() {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        memberRepository.deleteAll();

        memberRepository.save(member);
    }

    @Test
    void 주문_생성() {
        Order saveOrder = orderRepository.save(order);

        Optional<Order> optionalFindOrder = orderRepository.findById(saveOrder.getId());

        assertThat(optionalFindOrder.isPresent(), is(true));

        Order findOrder = optionalFindOrder.get();

        assertThat(findOrder.getId(), is(saveOrder.getId()));
    }

    @Test
    void 주문아이템_생성() {
        Order saveOrder = orderRepository.save(order);
        OrderItem saveOrderItem = orderItemRepository.save(orderItem);

        saveOrder.addOrderItem(saveOrderItem);

        Optional<OrderItem> optionalFindOrderItem = orderItemRepository.findById(saveOrderItem.getId());

        assertThat(optionalFindOrderItem.isPresent(), is(true));

        OrderItem findOrderItem = optionalFindOrderItem.get();

        assertThat(findOrderItem.getId(), is(saveOrderItem.getId()));
        assertThat(findOrderItem.getOrder().getId(), is(saveOrder.getId()));
        assertThat(saveOrder.getOrderItems().size(), is(1));
    }
}
