package com.sehee.weeklyjpa;

import com.sehee.weeklyjpa.domain.order.Item;
import com.sehee.weeklyjpa.domain.order.Member;
import com.sehee.weeklyjpa.domain.order.Order;
import com.sehee.weeklyjpa.domain.order.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.sehee.weeklyjpa.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;
    Order order;
    Member member;
    OrderItem orderItem;
    Item item;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    void persistNewOrderWithoutMemberAndOrderItem() {
        order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("This is memo.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);
        log.info("persist order!");
    }

    void persistNewMemberWithoutOrder() {
        member = new Member();
        member.setName("sehee");
        member.setNickName("LeeSehee");
        member.setAge(25);
        member.setAddress("Seoul");
        member.setDescription("This is description.");

        entityManager.persist(member);
        log.info("persist member!");
    }

    @Test
    @DisplayName("양방향 관계 저장")
    void mappingOrderAndMember() {
        transaction.begin();
        persistNewOrderWithoutMemberAndOrderItem();
        persistNewMemberWithoutOrder();

        order.setMember(member); // 둘 다 세팅
        member.getOrders().add(order); // 둘 다 세팅

        transaction.commit();

        Member member1 = entityManager.find(Order.class, order.getUuid()).getMember();
        assertThat(member1.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("편의메소드를 이용하여 양방향 관계 저장")
    void mappingOrderAndMemberWithMethod() {
        transaction.begin();
        persistNewOrderWithoutMemberAndOrderItem();
        persistNewMemberWithoutOrder();

        member.addOrder(order); // 한 번만 세팅

        transaction.commit();

        Member member1 = entityManager.find(Order.class, order.getUuid()).getMember();
        assertThat(member1.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("객체 그래프 탐색을 이용한 조회")
    void readBySearchingObjectGraph() {
        transaction.begin();
        persistNewOrderWithoutMemberAndOrderItem();
        persistNewMemberWithoutOrder();
        order.setMember(member); // 주문 -> 회원만 보기 가능한 상태. 직접 만든 setMember가 아니라 기본 setter로 해야합니당.

        transaction.commit();

        entityManager.clear(); // order -> 준영속 상태

        Member retrievedMember = entityManager.find(Member.class, member.getId());
        assertThat(retrievedMember.getId()).isEqualTo(member.getId());

        Order retrievedOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(retrievedOrder.getUuid()).isEqualTo(order.getUuid());

        log.info("{} and {}", order.getMember().getOrders().size(), entityManager.contains(order)); // order -> 준영속 상태
        assertThat(member.getOrders().size()).isEqualTo(0); // member에는 등록되지 않았다.
        log.info("{}", retrievedOrder.getMember().getOrders().size()); // 그래프 탐색 + commit 된 후 불러온 것에는 자동으로 되어있다.
        log.info("{}", retrievedMember.getOrders().size()); // 그래프 탐색 + commit 된 후 불러온 것에는 자동으로 되어있다.
    }
}
