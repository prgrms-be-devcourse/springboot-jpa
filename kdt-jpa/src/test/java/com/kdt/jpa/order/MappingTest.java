package com.kdt.jpa.order;

import com.kdt.jpa.order.domain.Food;
import com.kdt.jpa.order.domain.Item;
import com.kdt.jpa.order.domain.Member;
import com.kdt.jpa.order.domain.Order;
import com.kdt.jpa.order.domain.OrderStatus;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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

    @Test
    @DisplayName("양방향 연관관계 테스트")
    void twoWayAssociation() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member member = new Member();
        member.setName("CHOI");
        member.setNickName("C");
        member.setAddress("서울시");
        member.setAge(27);
        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("memo");
        order.setOrderStatus(OrderStatus.OPEND);
        order.setMember(member);
        em.persist(order);

        em.flush();
        em.clear(); // 영속성 컨텍스트 초기화

        Order findOrder = em.find(Order.class, order.getUuid());
        List<Order> orders = findOrder.getMember().getOrders();
        for (Order order1 : orders) {
            System.out.println(order1.getMemo());
        }
    }

    @Test
    @DisplayName("상속 관계 매핑 테스트")
    void inheritanceMappingTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Food food = new Food();
        food.setPrice(1000);
        food.setStockQuantity(100);
        food.setChef("CHOI");

        em.persist(food);

        ts.commit();
    }

    @Test
    @DisplayName("프록시 객체 생성")
    void proxyEntityTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member member = new Member();
        member.setName("CHOI");
        member.setNickName("C");
        member.setAddress("서울시");
        member.setAge(27);
        em.persist(member);

        em.flush();
        em.clear();

        Member findMember = em.getReference(Member.class, member.getId()); // 프록시 객체 조회

        // DB조회를 통해서(SELECT) 실제 Entity 생성
        findMember.getName();

        ts.commit();
        em.close();
    }

    @Test
    @DisplayName("준영속 상태일 때, 프록시 초기화시 에러발생")
    void proxyEntityTest2() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member member = new Member();
        member.setName("CHOI");
        member.setNickName("C");
        member.setAddress("서울시");
        member.setAge(27);
        em.persist(member);

        em.flush();
        em.clear();

        Member reference = em.getReference(Member.class, member.getId());
        System.out.println(reference.getClass().getName());

        em.detach(reference);

        Assertions.assertThatThrownBy(() -> reference.getName())
            .isInstanceOf(LazyInitializationException.class);
    }

    @Test
    @DisplayName("영속화 전이 성공")
    void persistenceTransitionTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member member = new Member();
        member.setName("CHOI");
        member.setNickName("C");
        member.setAddress("서울시");
        member.setAge(27);
        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("memo");
        order.setOrderStatus(OrderStatus.OPEND);
        order.setMember(member);
        em.persist(order);

        // Cascade.All 설정으로 인해 order, member 영속화 전이된다.
        em.persist(order);

        ts.commit();
    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction ts = em.getTransaction();
        ts.begin();

        Member member = new Member();
        member.setName("CHOI");
        member.setNickName("C");
        member.setAddress("서울시");
        member.setAge(27);
        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("memo");
        order.setOrderStatus(OrderStatus.OPEND);
        order.setMember(member);
        em.persist(order);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());

        // order, member 모두 삭제 (orphanRemoval = true)
        em.remove(findMember);

        ts.commit();
    }
}
