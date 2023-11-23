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

        Member member = Member.builder()
                .name("parkeugene")
                .nickName("보그리")
                .address("서울시 노원구")
                .age(23)
                .createdBy("eugene").build();

        entityManager.persist(member);

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("부재시 연락주세요")
                .createdBy("eugene Park").build();
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
        Member member = Member.builder()
                .name("parkeugene")
                .nickName("보그리")
                .address("서울시 노원구")
                .age(23)
                .createdBy("eugene").build();

        entityManager.persist(member);

        // 주문
        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .createdBy("eugene Park")
                .memo("---").build();
        order.setMember(member);

        entityManager.persist(order);

        // 상품
        Item item = Item.builder()
                .price(1200)
                .stockQuantity(20)
                .createdBy("eugene")
                .build();

        entityManager.persist(item);

        OrderItem orderItem = OrderItem.builder()
                .quantity(3)
                .createdBy("eugene").build();

        orderItem.setOrder(order);
        orderItem.addItem(item);

        item.setOrderItem(orderItem);
        order.addOrderItem(orderItem);

        entityManager.persist(orderItem);


        transaction.commit();

        Order entity = entityManager.find(Order.class, order.getUuid());
        log.info("orderItems size : {}, orderItems.getQuantity : {}", entity.getOrderItems().size(), entity.getOrderItems().get(0).getQuantity());
    }
}
