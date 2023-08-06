package com.programmers.jpa_mission.domain;

import com.programmers.jpa_mission.domain.order.Item;
import com.programmers.jpa_mission.domain.order.Member;
import com.programmers.jpa_mission.domain.order.Order;
import com.programmers.jpa_mission.domain.order.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.programmers.jpa_mission.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void 양방향관계_저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);

        Member member = new Member();
        member.setName("beomchul");
        member.setNickName("beombu");
        member.setAge(26);
        member.setAddress("서울시 노원구");
        member.setDescription("KDT 화이팅");

        member.getOrders().add(order);

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 양방향관계_저장2() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setOrderItems(new ArrayList<>());

        entityManager.persist(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setPrice(1000);

        order.addOrderItem(orderItem);
        entityManager.persist(orderItem);

        Item item = new Item();
        item.setPrice(1000);
        item.setStockQuantity(5);

        orderItem.addItem(item);
        entityManager.persist(item);

        transaction.commit();
    }

    @Test
    void 양방향관계_저장_편의메소드() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);

        Member member = new Member();
        member.setName("beomchul");
        member.setNickName("beombu");
        member.setAge(26);
        member.setAddress("서울시 노원구");
        member.setDescription("KDT 화이팅");

        member.addOrder(order);
        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 객체그래프탐색을_이용한_조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);

        Member member = new Member();
        member.setName("beomchul");
        member.setNickName("beombu");
        member.setAge(26);
        member.setAddress("서울시 노원구");
        member.setDescription("KDT 화이팅");
        member.addOrder(order);

        entityManager.persist(member);
        transaction.commit();
        entityManager.clear();

        Member findMember = entityManager.find(Member.class, 1L);
        log.info("order-memo: {}", findMember.getOrders().get(0).getMemo());

        Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getUuid());
        log.info("member-nickName: {}", findOrder.getMember().getNickName());
    }
}
