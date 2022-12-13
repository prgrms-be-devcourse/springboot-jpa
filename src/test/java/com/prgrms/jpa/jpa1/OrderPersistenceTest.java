package com.prgrms.jpa.jpa1;

import com.prgrms.jpa.jpa1.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

    }

    @AfterEach
    void cleanUp() {
        entityManager.clear();
        entityManager.close();
    }

    @Test
    @DisplayName("고객과 주문 사이에 양방향 관계를 저장할 수 있다.")
    void test_relation_between_Member_and_Order() {
        // given
        String uuid = UUID.randomUUID().toString();

        Member member = new Member();
        member.setName("이수린");
        member.setAddress("경기도 어딘가");
        member.setAge(24);
        member.setNickName("뚜린");
        member.setDescription("데브코스 3기 백둥이입니다.");

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("부재시 문 앞에 놔주세요");
        order.setMember(member); // 객체를 통해 저장

        // when
        entityManager.persist(order);
        entityTransaction.commit();

        // then
        Order findOrder = entityManager.find(Order.class, uuid);
        Member findMemberByOrder = findOrder.getMember();

        assertThat(findOrder).isEqualTo(order);
        assertThat(member).isEqualTo(findMemberByOrder);
    }

    @Test
    @DisplayName("Order에서 상속받은 Base 엔티티 값인 createdAt, createdBy 값을 지정하고, 가져올 수 있다.")
    void mapped_super_class_test() {
        // given
        String uuid = UUID.randomUUID().toString();

        Order order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("---");
        order.setOrderDateTime(LocalDateTime.now());

        // when
        LocalDateTime createAt = LocalDateTime.now();
        String createdBy = "surin";

        order.setCreatedAt(createAt);
        order.setCreateBy(createdBy);

        entityManager.persist(order);
        entityTransaction.commit();

        // then
        Order findOrder = entityManager.find(Order.class, uuid);
        LocalDateTime findCreatedAt = findOrder.getCreatedAt();
        String findCreatedBy = findOrder.getCreateBy();

        assertThat(findCreatedAt).isEqualTo(createAt);
        assertThat(findCreatedBy).isEqualTo(createdBy);
    }

    @Test
    @DisplayName("주문 아이템과 상품(아이템) 사이애 양방향 관계로 저장하고 조회할 수 있다.")
    void test_relation_between_Item_and_OrderItem() {
        Item item = new Item();
        item.setPrice(3000);
        item.setStock(100);

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(1);
        orderItem.setPrice(
                item.getPrice());

        // when
        orderItem.setItem(item);

        entityManager.persist(orderItem);
        entityManager.persist(item);
        entityTransaction.commit();

        // then
        Long orderItemId = orderItem.getId();
        Long itemId = item.getId();

        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItemId);
        Item findItem = entityManager.find(Item.class, itemId);

        OrderItem findOrderItemByItem = findItem.getOrderItems()
                .get(0);
        Item findItemByOrderItem = findOrderItem.getItem();

        assertThat(findOrderItemByItem).isEqualTo(orderItem);
        assertThat(findItemByOrderItem).isEqualTo(item);
    }

    @Test
    @DisplayName("주문과 주문 아이템 사이에 양방향 관계로 저장하고 조회할 수 있다.")
    void test_relation_between_order_and_orderItem() {
        // given
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(1);
        orderItem.setPrice(30000);

        String uuid = UUID.randomUUID().toString();

        Order order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("---");
        order.setOrderDateTime(LocalDateTime.now());

        // when
        orderItem.setOrder(order);

        entityManager.persist(orderItem);
        entityManager.persist(order);
        entityTransaction.commit();

        // then
        Order findOrder = entityManager.find(Order.class, uuid);
        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        OrderItem findOrderItemByOrder = findOrder.getOrderItems()
                .get(0);
        Order findOrderByOrderItem = findOrderItem.getOrder();

        assertThat(findOrderByOrderItem).isEqualTo(order);
        assertThat(findOrderItemByOrder).isEqualTo(orderItem);
    }
}
