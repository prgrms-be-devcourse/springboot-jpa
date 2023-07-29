package com.programmers.jpa;

import com.programmers.jpa.domain.Item;
import com.programmers.jpa.domain.Member;
import com.programmers.jpa.domain.Order;
import com.programmers.jpa.domain.OrderItem;
import com.programmers.jpa.repository.ItemRepository;
import com.programmers.jpa.repository.MemberRepository;
import com.programmers.jpa.repository.OrderItemRepository;
import com.programmers.jpa.repository.OrderRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderItemAssociationTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    TestEntityManager em;

    Member givenMember;
    Order givenOrder;
    Item givenItem;

    @BeforeEach
    void init() {
        givenMember = new Member("firstName", "lastName");
        givenOrder = new Order("memo", givenMember);
        givenItem = new Item("name", 1000, 100);
        memberRepository.save(givenMember);
        orderRepository.save(givenOrder);
        itemRepository.save(givenItem);
    }

    @Test
    @DisplayName("성공: 다대일 단방향 lazy loading 테스트")
    void manyToOne_lazyLoading() {
        //given
        OrderItem orderItem = new OrderItem(1000, givenOrder, givenItem);
        orderItemRepository.save(orderItem);
        em.flush();
        em.clear();

        //when
        OrderItem findOrderItem = orderItemRepository.findById(orderItem.getId()).get();

        //then
        boolean orderLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrderItem.getOrder());
        boolean itemLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrderItem.getItem());
        assertThat(orderLoaded).isFalse();
        assertThat(itemLoaded).isFalse();
    }

    @Test
    @DisplayName("성공: 다대일 단방향 fetch join 테스트")
    void manyToOne_lazyLoading_fetchJoin() {
        //given
        OrderItem orderItem = new OrderItem(1000, givenOrder, givenItem);
        orderItemRepository.save(orderItem);
        em.flush();
        em.clear();

        //when
        OrderItem findOrderItem = orderItemRepository.findByIdWithOthers(orderItem.getId()).get();

        //then
        boolean itemLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrderItem.getItem());
        boolean orderLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrderItem.getOrder());
        boolean memberLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrderItem.getOrder().getMember());
        assertThat(itemLoaded).isTrue();
        assertThat(orderLoaded).isTrue();
        assertThat(memberLoaded).isTrue();
    }
}