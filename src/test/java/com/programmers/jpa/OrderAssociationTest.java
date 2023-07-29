package com.programmers.jpa;

import com.programmers.jpa.domain.Item;
import com.programmers.jpa.domain.Member;
import com.programmers.jpa.domain.Order;
import com.programmers.jpa.domain.OrderItem;
import com.programmers.jpa.repository.ItemRepository;
import com.programmers.jpa.repository.MemberRepository;
import com.programmers.jpa.repository.OrderItemRepository;
import com.programmers.jpa.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderAssociationTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    EntityManager em;
    @Autowired
    EntityManagerFactory emf;

    Member givenMember;

    @BeforeEach
    void setUp() {
        givenMember = new Member("firstName", "lastName");
        memberRepository.save(givenMember);
    }

    @Test
    @DisplayName("성공: 다대일 단방향 lazy loading 테스트")
    void manyToOne_lazyLoading() {
        //given
        Order order = new Order("memo", givenMember);
        orderRepository.save(order);
        em.flush();
        em.clear();

        //when
        Order findOrder = orderRepository.findById(order.getId()).get();

        //then
        boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrder.getMember());
        assertThat(isLoaded).isFalse();
    }

    @Test
    @DisplayName("성공: 다대일 단방향 fetch join 테스트")
    void manyToOne_lazyLoading_fetchJoin() {
        //given
        Order order = new Order("memo", givenMember);
        orderRepository.save(order);
        em.flush();
        em.clear();

        //when
        Order findOrder = orderRepository.findByIdWithMember(order.getId()).get();

        //then
        boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrder.getMember());
        assertThat(isLoaded).isTrue();
    }

    @Test
    @DisplayName("성공: 일대다 collection fetch join 테스트")
    void oneToMany_lazyLoading_collectionFetchJoin() {
        //given
        Order order = new Order("memo", givenMember);
        Item item = new Item("name", 1000, 10);
        OrderItem orderItemA = new OrderItem(1000, order, item);
        OrderItem orderItemB = new OrderItem(1000, order, item);
        orderRepository.save(order);
        itemRepository.save(item);
        orderItemRepository.save(orderItemA);
        orderItemRepository.save(orderItemB);
        em.flush();
        em.clear();

        //when
        Optional<Order> optionalOrder = orderRepository.findByIdWithOrderItems(order.getId());

        //then
        assertThat(optionalOrder).isNotEmpty();
        Order findOrder = optionalOrder.get();
        assertThat(findOrder.getOrderItems()).hasSize(2);
    }

    @Test
    @DisplayName("성공: 일대다 영속성 전이 persist")
    void cascade_persist() {
        //given
        Order order = new Order("memo", givenMember);
        Item item = new Item("name", 1000, 10);
        orderRepository.save(order);
        itemRepository.save(item);

        //when
        new OrderItem(1000, order, item);
        new OrderItem(1000, order, item);
        em.flush();
        em.clear();

        //then
        Order findOrder = orderRepository.findByIdWithOrderItems(order.getId()).get();
        assertThat(findOrder.getOrderItems()).hasSize(2);
    }

    @Test
    @DisplayName("성공: 일대다 고아 객체 제거")
    void orphanRemoval() {
        //given
        Order order = new Order("memo", givenMember);
        Item item = new Item("name", 1000, 10);
        OrderItem orderItem = new OrderItem(1000, order, item);
        orderRepository.save(order);
        itemRepository.save(item);
        orderItemRepository.save(orderItem);

        //when
        order.getOrderItems().remove(0);
        em.flush();
        em.clear();

        //then
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderItem.getId());
        assertThat(optionalOrderItem).isEmpty();
    }
}