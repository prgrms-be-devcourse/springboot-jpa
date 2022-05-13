package com.kdt.jpa.order;

import com.kdt.jpa.order.domain.Member;
import com.kdt.jpa.order.domain.Order;
import com.kdt.jpa.order.domain.OrderStatus;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    Order order;
    Member member;

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        member = new Member();
        member.setName("CHOI");
        member.setNickName("C");
        member.setAddress("서울시");
        member.setAge(27);
        em.persist(member);

        order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("memo");
        order.setOrderStatus(OrderStatus.OPEND);
        order.setMember(member);
        em.persist(order);

        ts.commit();
    }

    @Test
    @DisplayName("단방향 연관관계")
    void test1() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Order findOrder = em.find(Order.class, order.getUuid());
        Member findMember = findOrder.getMember();
        log.info("{} {}", findMember.getId(), findMember.getName());

        ts.commit();
    }

    @Test
    @DisplayName("양방향 연관관계")
    void Test2() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Order findOrder = em.find(Order.class, order.getUuid());
        List<Order> orders = findOrder.getMember().getOrders();
        for (Order order1 : orders) {
            System.out.println(order1.getMemo());
        }
    }

    @Test
    @DisplayName("프록시 객체 생성")
    void Test3() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member findMember = em.getReference(Member.class, member.getId());
        System.out.println(findMember.getName());
    }

    @Test
    @DisplayName("준영속 상태일 때, 프록시 초기화시 에러발생")
    void test4() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member reference = em.getReference(Member.class, member.getId());
        System.out.println(reference.getClass().getName());

        em.detach(reference);

        Assertions.assertThatThrownBy(() -> reference.getName())
            .isInstanceOf(LazyInitializationException.class);
    }

    @Test
    @DisplayName("영속화 전이 성공")
    void test5() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member newMember = new Member();
        newMember.setName("CHOI");
        newMember.setNickName("CC");
        newMember.setAddress("서울시");
        newMember.setAge(27);

        Order newOrder = new Order();
        newOrder.setUuid(UUID.randomUUID().toString());
        newOrder.setMemo("memo");
        newOrder.setOrderStatus(OrderStatus.OPEND);
        newOrder.setMember(newMember);

        em.persist(newOrder);

        ts.commit();
    }

    @Test
    @DisplayName("고아 객체 제거")
    public void test6() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member findMember = em.find(Member.class, this.member.getId());
        em.remove(findMember);
        // order, member 모두 삭제
        ts.commit();
    }
}
