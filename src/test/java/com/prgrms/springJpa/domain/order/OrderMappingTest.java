package com.prgrms.springJpa.domain.order;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @AfterEach
    void tearDown() {
        transaction.begin();

        entityManager.createQuery("DELETE FROM OrderItem ").executeUpdate();
        entityManager.createQuery("DELETE FROM Order ").executeUpdate();
        entityManager.createQuery("DELETE FROM Member ").executeUpdate();
        entityManager.createQuery("DELETE FROM Item ").executeUpdate();

        transaction.commit();
    }

    @DisplayName("주문을 통해 회원을 조회할 수 있다.")
    @Test
    void mapping_Order_And_Member() {
        // given
        transaction.begin();

        Member member = createMember();
        entityManager.persist(member);

        Order order = createOrder();
        order.changeMember(member);
        entityManager.persist(order);

        transaction.commit();

        // when
        entityManager.clear();
        Order findOrder = entityManager.find(Order.class, order.getUuid());

        // then
        assertThat(findOrder.getMember().getId()).isEqualTo(member.getId());
    }

    @DisplayName("회원을 통해 주문을 조회할 수 있다.")
    @Test
    void mapping_Member_And_Order() {
        // given
        transaction.begin();

        Member member = createMember();
        entityManager.persist(member);

        Order order = createOrder();
        member.addOrder(order);
        entityManager.persist(order);

        transaction.commit();

        // when
        entityManager.clear();
        Member findMember = entityManager.find(Member.class, member.getId());

        // then
        assertThat(findMember.getOrders().get(0)).usingRecursiveComparison().isEqualTo(order);
    }

    @DisplayName("주문 아이템을 통해 주문을 조회할 수 있다.")
    @Test
    void mapping_OrderItem_And_Order() {
        // given
        transaction.begin();

        OrderItem orderItem = createOrderItem();

        Order order = createOrder();
        order.addOrderItem(orderItem);
        entityManager.persist(order);

        transaction.commit();

        // when
        entityManager.clear();
        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());

        // then
        assertThat(findOrderItem.getOrder().getUuid()).isEqualTo(order.getUuid());
    }

    @DisplayName("주문 아이템을 통해 아이템을 조회할 수 있다.")
    @Test
    void mapping_OrderItem_And_Item() {
        // given
        transaction.begin();

        Item item = createItem();
        entityManager.persist(item);

        OrderItem orderItem = createOrderItem();
        orderItem.changeItem(item);
        entityManager.persist(orderItem);

        transaction.commit();

        // when
        entityManager.clear();
        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());

        // then
        assertThat(findOrderItem.getItem().getId()).isEqualTo(item.getId());
    }

    @DisplayName("주문을 영속화하면 주문아이템도 영속화된다.")
    @Test
    void order_Persist_With_OrderItem() {
        // given
        transaction.begin();

        Member member = createMember();
        entityManager.persist(member);

        Order order = createOrder();
        order.changeMember(member);
        entityManager.persist(order);

        transaction.commit();

        // when
        transaction.begin();

        OrderItem orderItem = createOrderItem();
        order.addOrderItem(orderItem);

        transaction.commit();

        // then
        assertThat(entityManager.contains(orderItem)).isTrue();
        assertThat(orderItem.getId()).isNotNull();
    }

    @DisplayName("주문 삭제 시 주문 아이템도 삭제된다.")
    @Test
    void order_Remove_With_OrderItem() {
        // given
        transaction.begin();

        Order order = createOrder();
        OrderItem orderItem = createOrderItem();
        order.addOrderItem(orderItem);
        entityManager.persist(order);

        transaction.commit();

        // when
        transaction.begin();

        entityManager.remove(order);

        transaction.commit();

        // then
        assertThat(entityManager.contains(orderItem)).isFalse();
    }

    @DisplayName("주문으로부터 삭제된 주문아이템은 삭제된다.")
    @Test
    void order_RemoveOrderItem() {
        // given
        transaction.begin();

        Member member = createMember();
        entityManager.persist(member);

        Order order = createOrder();
        order.changeMember(member);

        OrderItem orderItem = createOrderItem();
        order.addOrderItem(orderItem);

        entityManager.persist(order);

        transaction.commit();

        // when
        transaction.begin();

        order.removeOrderItem(orderItem);

        transaction.commit();

        // then
        assertThat(entityManager.find(OrderItem.class, orderItem.getId())).isNull();
    }

    private Order createOrder() {
        return new Order(UUID.randomUUID().toString(), "문앞에 놔주세요.", OrderStatus.OPENED, LocalDateTime.now());
    }

    private Member createMember() {
        return new Member("yuminhwan", "hwan", 26, "경북 구미시 흥안로");
    }

    private OrderItem createOrderItem() {
        return new OrderItem(1000, 100);
    }

    private Item createItem() {
        return new Item(1000, 200);
    }

}
