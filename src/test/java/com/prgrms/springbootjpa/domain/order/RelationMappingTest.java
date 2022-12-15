package com.prgrms.springbootjpa.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.prgrms.springbootjpa.domain.order.OrderStatus.*;

@Slf4j
@SpringBootTest
public class RelationMappingTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("연관관계에 의해 영속성 전이가 발생한다.")
    void relationMappingTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = createMember();
        Order order = createOrder();
        OrderItem orderItem = createOrderItem();
        Item item = createItem();
        member.addOrder(order);
        order.addOrderItem(orderItem);
        item.addOrderItem(orderItem);

        // order 엔티티 영속화 (Cascade 옵션에 의해 연관관계 매핑, 영속성 전이가 이루어짐)
        entityManager.persist(order);

        transaction.commit();

        // 영속성 컨텍스트 초기화
        entityManager.clear();

        // DB에서 가져와서 아래처럼 출력하면 order만 영속화 했음에도 모두 잘 매핑된 것을 확인할 수 있음
        Order entity = entityManager.find(Order.class, order.getUuid());
        log.info("Member Name: {}", entity.getMember().getName());
        log.info("Order Id: {}", entity.getUuid());
        log.info("OrderItem from Order: {} (Size = {})", entity.getOrderItems(), entity.getOrderItems().size());
        log.info("Order from OrderItem: {} (ID = {})", entity.getOrderItems().get(0).getOrder(), entity.getOrderItems().get(0).getOrder().getUuid());
        log.info("Item from OrderItem: {} (ID = {})", entity.getOrderItems().get(0).getItem(), entity.getOrderItems().get(0).getItem().getId());
        log.info("OrderItem from Item: {} (Size = {})", entity.getOrderItems().get(0).getItem().getOrderItems(), entity.getOrderItems().get(0).getItem().getOrderItems().size());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("Kim Changgyu");
        member.setNickName("pidgey");
        member.setAge(25);
        member.setAddress("서울특별시");
        member.setDescription("백엔드 데브코스 3기");
        member.setCreatedBy("changgyu");
        member.setCreatedAt(LocalDateTime.now());

        return member;
    }

    private Order createOrder() {
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setCreatedBy("changgyu");
        order.setCreatedAt(LocalDateTime.now());

        return order;
    }

    private OrderItem createOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(10000);
        orderItem.setQuantity(10);
        orderItem.setCreatedBy("changgyu");
        orderItem.setCreatedAt(LocalDateTime.now());

        return orderItem;
    }

    private Item createItem() {
        Item item = new Item();
        item.setPrice(1000);
        item.setStockQuantity(100);
        item.setCreatedBy("changgyu");
        item.setCreatedAt(LocalDateTime.now());

        return item;
    }
}
