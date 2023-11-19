package com.example.kdtjpa.domain;

import com.example.kdtjpa.domain.customer.CustomerRepository;
import com.example.kdtjpa.domain.order.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void 연관관계_테스트() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setName("parkeugene");
        member.setNickName("보그리");
        member.setAddress("서울시 노원구");
        member.setAge(23);

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재시 연락주세요.");
        order.setMember(member);

        entityManager.persist(order);

        transaction.commit();

        entityManager.clear();
        Order entity = entityManager.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName());
        log.info("{}", entity.getMember().getOrders().size());
    }

    @Test
    void 주문_연관관계_테스트() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        // 회원
        Member member = new Member();
        member.setName("parkeugene");
        member.setNickName("보그리");
        member.setAddress("서울시 노원구");
        member.setAge(23);
        member.setCreatedAt(LocalDateTime.now());
        member.setCreatedBy("eugene");

        entityManager.persist(member);

        // 주문
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재시 연락주세요.");
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy("eugene");
        order.setMember(member);

        entityManager.persist(order);

        // 상품
        Item item = new Item();
        item.setId(1L);
        item.setPrice(1500);
        item.setStockQuantity(10);
        item.setCreatedBy("eugene");
        item.setCreatedAt(LocalDateTime.now());

        Item mergedItem = entityManager.merge(item);

        // 주문 아이템
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setQuantity(3);
        orderItem.addItem(mergedItem);
        orderItem.setCreatedBy("eugene");
        orderItem.setCreatedAt(LocalDateTime.now());
        item.setOrderItem(orderItem);
        order.addOrderItem(orderItem);

        entityManager.persist(orderItem);

        transaction.commit();

        Order entity = entityManager.find(Order.class, order.getUuid());
        log.info("orderItems size : {}, orderItems.getQuantity : {}", entity.getOrderItems().size(), entity.getOrderItems().get(0).getQuantity());
    }
}
