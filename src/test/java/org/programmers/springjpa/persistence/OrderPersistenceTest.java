package org.programmers.springjpa.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.springjpa.domain.entity.Member;
import org.programmers.springjpa.domain.entity.Order;
import org.programmers.springjpa.domain.entity.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private EntityTransaction transaction;
    private Order order;
    private Member member;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        order = new Order();
        order.setId(UUID.randomUUID());
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("memo");

        member = new Member();
        member.setName("Jiin Hong");
        member.setNickName("JIN-076");
        member.setAge(25);
        member.setAddress("Incheon, Republic of Korea");
        member.setDescription("description");
    }

    @Test
    void save() {
        transaction.begin();

        entityManager.persist(order);
        member.getOrders().add(order);
        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void saveWithSetMethod() {
        transaction.begin();

        entityManager.persist(order);
        member.addOrder(order);
        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void find() {
        transaction.begin();

        entityManager.persist(order);
        member.addOrder(order);
        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        Member expectedMember = entityManager.find(Member.class, 1L);
        Order expectedOrder = entityManager.find(Order.class, expectedMember.getOrders().get(0).getId());
    }
}
