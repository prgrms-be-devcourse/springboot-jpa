package com.pppp0722.springbootjpa.domain.order;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    OrderRepository repository;

    @Autowired
    EntityManagerFactory emf;

    String orderId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        if (repository.findById(orderId).isPresent()) {
            repository.deleteById(orderId);
        }
    }

    @Test
    void 저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(orderId);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("---");
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(".");

        entityManager.persist(order);

        transaction.commit();
    }

    @Test
    void 조회_DB조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(orderId);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("---");
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(".");

        entityManager.persist(order);

        transaction.commit();

        entityManager.detach(order);

        Order selected = entityManager.find(Order.class, orderId);
        log.info("{} {}", selected.getOrderStatus(), selected.getMemo());
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(orderId);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("---");
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(".");

        entityManager.persist(order);

        transaction.commit();

        Order selected = entityManager.find(Order.class, orderId);
        log.info("{} {}", selected.getOrderStatus(), selected.getMemo());
    }

    @Test
    void 수정() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(orderId);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("---");
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(".");

        entityManager.persist(order);

        transaction.commit();

        transaction.begin();

        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setMemo("===");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(orderId);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("---");
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(".");

        entityManager.persist(order);

        transaction.commit();

        transaction.begin();

        entityManager.remove(order);

        transaction.commit();
    }
}