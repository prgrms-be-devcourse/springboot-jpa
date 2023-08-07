package com.example.weeklyjpa.domain;

import com.example.weeklyjpa.domain.member.Member;
import com.example.weeklyjpa.domain.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    private EntityManager entityManager;

    private EntityTransaction transaction;
    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
    }
    @Test
    @DisplayName("양방향관계를 저장한다.")
    void save_mapping_test() {
        transaction.begin();

        Order order = new Order();

        entityManager.persist(order);

        Member member = new Member("bona@gmail.com","bbona");

        order.setMember(member);// <- 연관관계 주인
        member.getOrderList().add(order); // 연관관계의 주인이 아닌곳에만 SETTING

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    @DisplayName("양방향관계 저장 편의메소드를 테스트한다.")
    void set_method_test() {
        transaction.begin();

        Order order = new Order();

        entityManager.persist(order);

        Member member = new Member("bona@gmail.com","bbona");

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    @DisplayName("객체그래프탐색을 이용하여 조회한다.")
    void find_object_graph_test() {
        transaction.begin();

        Order order = new Order();

        entityManager.persist(order);

        Member member = new Member("bona@gmail.com","bbona");


        order.setMember(member);// <- 연관관계 주인

        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, member.getId());
        log.info("order-id: {}", findMember.getOrderList().get(0).getId());

        // 주문조회 -> 주문한 회원 조회
        Order findOrder = entityManager.find(Order.class, findMember.getOrderList().get(0).getId());
        log.info("member-email: {}", findOrder.getMember().getEmail());
    }
}
