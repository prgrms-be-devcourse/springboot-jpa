package org.prgms.springbootjpa.mission3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.mission3.domain.member.Member;
import org.prgms.springbootjpa.mission3.domain.member.MemberRepository;
import org.prgms.springbootjpa.mission3.domain.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.prgms.springbootjpa.mission3.domain.order.OrderStatus.CREATED;

@SpringBootTest
class OrderItemItemRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    Member member = new Member("현지", "딩딩", 25, "서울시 마포구");
    Order order = new Order(CREATED, "집 앞 배송", member);
    Item item = new Item(1000, 100);
    OrderItem orderItem = new OrderItem(10000, 2, order, item);

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        memberRepository.deleteAll();

        memberRepository.save(member);
        orderRepository.save(order);
    }

    @Test
    void 아이템_생성() {
        Item saveItem = itemRepository.save(item);

        Optional<Item> optionalFindItem = itemRepository.findById(saveItem.getId());

        assertThat(optionalFindItem.isPresent(), is(true));

        Item findItem = optionalFindItem.get();

        assertThat(findItem.getId(), is(saveItem.getId()));
    }

    @Test
    void 주문아이템_생성() {
        Item saveItem = itemRepository.save(item);
        OrderItem saveOrderItem = orderItemRepository.save(orderItem);

        saveItem.addOrderItem(saveOrderItem);

        Optional<OrderItem> optionalFindOrderItem = orderItemRepository.findById(saveOrderItem.getId());

        assertThat(optionalFindOrderItem.isPresent(), is(true));

        OrderItem findOrderItem = optionalFindOrderItem.get();

        assertThat(findOrderItem.getId(), is(saveOrderItem.getId()));
        assertThat(findOrderItem.getItem().getId(), is(saveItem.getId()));
        assertThat(saveItem.getOrderItems().size(), is(1));
    }
}
