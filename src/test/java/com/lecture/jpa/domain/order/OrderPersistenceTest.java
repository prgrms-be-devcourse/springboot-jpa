package com.lecture.jpa.domain.order;

import static com.lecture.jpa.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
    }

    @Test
    void 양방향관계_저장() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setId(1L);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.merge(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("beomu kim");
        member.setNickName("sonny");
        member.setAge(26);
        member.setAddress("서울시");

        member.getOrders().add(order); // 연관관계의 주인이 아닌곳에만 SETTING


        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 양방향관계_저장_편의메소드() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setId(1L);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.merge(order);

        Member member = new Member();
        member.setName("beomu kim");
        member.setNickName("sonny");
        member.setAge(26);
        member.setAddress("서울시");

        member.addOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 객체그래프탐색을_이용한_조회() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setId(1L);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.merge(order);

        Member member = new Member();
        member.setName("beomu kim");
        member.setNickName("sonny");
        member.setAge(26);
        member.setAddress("서울시");
        member.setDescription("화이팅");

        member.addOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);
        transaction.commit();

        entityManager.clear();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, member.getId());
        assertThat(findMember).isNotNull();

        System.out.println(findMember.getOrders());
        // 주문조회 -> 주문한 회원 조회
        Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getId());
        assertThat(findOrder).isNotNull();
    }
}
