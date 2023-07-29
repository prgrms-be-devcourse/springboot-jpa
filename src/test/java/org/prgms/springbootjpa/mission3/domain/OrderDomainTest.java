package org.prgms.springbootjpa.mission3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.mission3.domain.member.Member;
import org.prgms.springbootjpa.mission3.domain.member.MemberRepository;
import org.prgms.springbootjpa.mission3.domain.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.prgms.springbootjpa.mission3.domain.order.OrderStatus.CREATED;

@SpringBootTest
class OrderDomainTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    Member member = new Member("현지", "딩딩", 25, "서울시 마포구");
    Order order = new Order(CREATED, "집 앞 배송");
    Item item = new Item(1000, 100);
    OrderItem orderItem = new OrderItem(10000, 2);

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 멤버_생성() {
        Member saveMember = memberRepository.save(member);

        List<Member> members = memberRepository.findAll();

        assertThat(members.size(), is(1));
        assertThat(saveMember.getId(), notNullValue());
    }

    @Test
    void 아이템_생성() {
        Item saveItem = itemRepository.save(item);

        List<Item> items = itemRepository.findAll();

        assertThat(items.size(), is(1));
        assertThat(saveItem.getId(), notNullValue());
    }

    @Test
    void 아이템_주문아이템_생성() {
        itemRepository.save(item);

        item.addOrderItem(orderItem);

        orderItemRepository.save(orderItem);

        List<OrderItem> orderItems = item.getOrderItems();

        assertThat(item.getId(), notNullValue());
        assertThat(orderItems.size(), is(1));
        assertThat(orderItems.get(0), samePropertyValuesAs(orderItem));

        assertThat(orderItem.getId(), notNullValue());
        assertThat(orderItem.getItem(), samePropertyValuesAs(item));
    }

    @Test
    void 주문_주문아이템_생성() {
        order.addOrderItem(orderItem);
        orderRepository.save(order);

        List<OrderItem> orderItems = order.getOrderItems();

        assertThat(order.getId(), notNullValue());
        assertThat(orderItems.size(), is(1));
        assertThat(orderItems.get(0), samePropertyValuesAs(orderItem));

        assertThat(orderItem.getId(), notNullValue());
        assertThat(orderItem.getOrder(), samePropertyValuesAs(order));
    }

    @Test
    void 멤버_주문_생성() {
        memberRepository.save(member);

        member.addOrder(order);

        orderRepository.save(order);

        List<Order> orders = member.getOrders();

        assertThat(member.getId(), notNullValue());
        assertThat(orders.size(), is(1));
        assertThat(orders.get(0), samePropertyValuesAs(order));

        assertThat(order.getId(), notNullValue());
        assertThat(order.getMember(), samePropertyValuesAs(member));
    }

    @Test
    void 주문_도메인_테스트() {
        memberRepository.save(member);
        itemRepository.save(item);

        item.addOrderItem(orderItem);
        order.addOrderItem(orderItem);
        member.addOrder(order);

        orderRepository.save(order);

        assertThat(member.getId(), notNullValue());
        assertThat(member.getOrders().size(), is(1));
        assertThat(member.getOrders().get(0), samePropertyValuesAs(order));

        assertThat(order.getId(), notNullValue());
        assertThat(order.getMember(), samePropertyValuesAs(member));
        assertThat(order.getOrderItems().size(), is(1));
        assertThat(order.getOrderItems().get(0), samePropertyValuesAs(orderItem));

        assertThat(item.getId(), notNullValue());
        assertThat(item.getOrderItems().size(), is(1));
        assertThat(item.getOrderItems().get(0), samePropertyValuesAs(orderItem));

        assertThat(orderItem.getId(), notNullValue());
        assertThat(orderItem.getItem(), samePropertyValuesAs(item));
        assertThat(orderItem.getOrder(), samePropertyValuesAs(order));
    }
}
