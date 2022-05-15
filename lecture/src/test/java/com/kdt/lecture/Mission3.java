package com.kdt.lecture;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.lecture.domain.order.Item;
import com.kdt.lecture.domain.order.Member;
import com.kdt.lecture.domain.order.Order;
import com.kdt.lecture.domain.order.OrderItem;
import com.kdt.lecture.domain.order.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Mission3 {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void 양방향관계_저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //주문 엔티티
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화점요");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        entityManager.persist(order);
        //회원 엔티티
        Member member = new Member();
        member.setName("hunkiKim");
        member.setNickName("gnsrl");
        member.setAge(27);
        member.setAddress("서울시 서대문구");
        member.setDescription("졸렵네요");

        // order가 연관관계의 주인이다.
        member.getOrders().add(order); //연관 관계의 주인이 아닌 곳에 SETTING한다.
        entityManager.persist(member);
        transaction.commit();

        assertThat(entityManager.find(Member.class, member.getId()).getName()).isEqualTo(
            member.getName());
    }

    @Test
    void 양방향관계_저장_편의메서드() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //주문 엔티티
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화점요");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        entityManager.persist(order);
        //회원 엔티티
        Member member = new Member();
        member.setName("hunkiKim");
        member.setNickName("gnsrl.rla");
        member.setAge(27);
        member.setAddress("서울시 서대문구");
        member.setDescription("졸렵네요");
        //편의메서드
        member.addOrder(order);
        entityManager.persist(member);
        transaction.commit();

        assertThat(entityManager.find(Order.class, order.getUuid()).getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    void Order_양방향_조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //주문 엔티티
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화점요");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        entityManager.persist(order);
        //주문 아이템 엔티티
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(3);
        orderItem.setOrder(order);
        entityManager.persist(orderItem);
        //아이템 엔티티
        Item item = new Item();
        item.setPrice(1500);
        item.setStockQuantity(15);
        item.setOrderItem(orderItem);
        entityManager.persist(item);
        transaction.commit();
        entityManager.clear();

        Order selectedOrder = entityManager.find(Order.class, order.getUuid());
        log.info("order : {}", selectedOrder.getMemo());
        OrderItem findOrderItem = entityManager.find(OrderItem.class, order.getOrderItems().get(0).getId());
        assertThat(findOrderItem.getPrice()).isEqualTo(orderItem.getPrice());
        Item findItem = entityManager.find(Item.class, findOrderItem.getItems().get(0).getId());
        assertThat(findItem.getPrice()).isEqualTo(findItem.getPrice());
    }
}
