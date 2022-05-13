package com.will.jpapractice.domain;

import static com.will.jpapractice.domain.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;


@DataJpaTest
@Slf4j
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
    }

    @Test
    void 주문_생성_후_회원에_주문_등록() {
        // Given
        Member member = createMember();

        // When
        Order newOrder = new Order(member);
        Order memberOrder = member.getOrders().get(0);

        // Then
        assertThat(memberOrder).usingRecursiveComparison().isEqualTo(newOrder);
    }

    @Test
    void 주문상품_생성_후_주문에_주문상품_등록() {
        // Given
        Member member = createMember();
        Order order = new Order(member);
        Item newItem = createItem();

        // When
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setOrder(order);

        // Then
        OrderItem orderItem = order.getOrderItems().get(0);
        assertThat(orderItem).usingRecursiveComparison().isEqualTo(newOrderItem);
    }

    @Test
    void 주문_삭제() {
        // Given
        Member member = createMember();
        Order order = new Order(member);
        order.setUuid(UUID.randomUUID().toString());

        Item newItem = createItem();

        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setOrder(order);
        newOrderItem.setItem(newItem);

        entityManager.persist(order);

        // When
        entityManager.remove(order);

        // Then
        Order selectedOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(selectedOrder).isNull();
    }

    @Test
    void 연관관계_테스트() {
        // Given
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        Member member = createMember();

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("---");
        order.setMember(member);

        entityManager.persist(order);

        tx.commit();

        // When
        entityManager.clear();
        Order selectedOrder = entityManager.find(Order.class, order.getUuid());
        Member selectedMember = entityManager.find(Member.class, member.getId());

        // Then
        assertThat(selectedOrder.getMember().getNickName()).isEqualTo(member.getNickName());
        assertThat(selectedMember.getOrders()).hasSizeGreaterThan(0);
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("will");
        member.setNickName("jake");
        member.setAge(23);
        member.setAddress("관악");

        entityManager.persist(member);
        return member;
    }

    private Item createItem() {
        Item item = new Item(20000, 5);
        entityManager.persist(item);

        return item;
    }
}
