package com.kdt.lecture.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


@Slf4j
@DataJpaTest
class OrderPersistenceContextTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("양방향 연관관계 메서드 테스트")
    void setMemberTest(){

        var em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        var order = new Order(UUID.randomUUID().toString(), OrderStatus.OPENED, LocalDateTime.now(), "--_--", null);
        em.persist(order);

        var member = new Member("hj", "www123", 20, "경기도", "--");

        //연관관계 메서드 수행
        order.setMember(member);
        em.persist(member);     //INSERT, INSERT, UPDATE 쿼리 실행

        transaction.commit();
        assertThat(member.getOrders(),hasSize(1));

        String uuid = member.getOrders().get(0).getUuid();
        assertThat(uuid, is(equalTo(order.getUuid())));

        em.clear();
        var selectedOrder = em.find(Order.class, order.getUuid());
        assertThat(selectedOrder.getMember().getId(), is(equalTo(member.getId())));
    }

    @Test
    @DisplayName("객체 그래프 탐색 테스트")
    void lazyLoadingTest(){

        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();

        var member = new Member("hj", "www123", 20, "경기도", "--");
        em.persist(member);

        //Order
        var order = new Order(UUID.randomUUID().toString(), OrderStatus.OPENED, LocalDateTime.now(), "--_--", member);
        em.persist(order);

        var item = new Item("mouse", 100000, 2);
        em.persist(item);

        var orderItem = new OrderItem(OrderStatus.OPENED, order, item);
        em.persist(orderItem);

        transaction.commit();
        em.clear();

        log.info("select 쿼리 수행");
        var selected = em.find(Order.class, order.getUuid());

        log.info("EAGER fetchType. 쿼리 수행되지 않음");
        assertThat(selected.getMember().getName(), is(equalTo(member.getName())));

        log.info("LAZY fetchType. 쿼리 수행");
        assertThat(selected.getItems(), hasSize(1));

    }

}