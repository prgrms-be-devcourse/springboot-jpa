package com.kdt.jpa.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderMemberMappingTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private EntityTransaction transaction;

    private static final String orderId = UUID.randomUUID().toString();
    private long memberId;

    @BeforeEach
    void setData() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        transaction.begin();
        Member member = new Member("taehee", "hello", 28, "경기도 수원시");
        entityManager.persist(member);

        Order order = new Order(orderId, LocalDateTime.now(), OrderStatus.OPENED, "문앞에 놔주세요.");
        order.setMember(member);
        entityManager.persist(order);

        transaction.commit();
        memberId = member.getId();
    }

    @AfterEach
    void clean() {
        transaction.begin();
        entityManager.createQuery("DELETE FROM Order ").executeUpdate();
        entityManager.createQuery("DELETE FROM Member ").executeUpdate();
        transaction.commit();
    }

    @Test
    @DisplayName("회원을 통해 주문을 조회한다.")
    void findOrderInMember() {
        // given
        entityManager.clear();

        // when
        Member result = entityManager.find(Member.class, memberId);

        // then
        assertThat(result.getOrders().get(0).getMemo())
                .isEqualTo("문앞에 놔주세요.");
    }

    @Test
    @DisplayName("주문을 통해 회원을 조회한다.")
    void findMemberInOrder() {
        // given
        entityManager.clear();

        // when
        Order result = entityManager.find(Order.class, orderId);

        // then
        assertThat(result.getMember().getName())
                .isEqualTo("taehee");
    }
}
