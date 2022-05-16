package com.example.springbootjpa.mission3;

import com.example.springbootjpa.mission1.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Mission3Test {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert(){
        Member member = new Member();
        member.setName("tester");
        member.setAge(20);
        member.setAddress("Address Test");
        member.setNickName("nick.tester");
        member.setDescription("Description Test");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);

        transaction.commit();
        entityManager.clear();

        Member foundMember = entityManager.find(Member.class, member.getId());

        assertThat(foundMember).usingRecursiveComparison().isEqualTo(member);
    }

    @Test
    void order_insert(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Member member = new Member();
        member.setName("tester");
        member.setAge(20);
        member.setAddress("Address Test");
        member.setNickName("nick.tester");
        member.setDescription("Description Test");

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("Memo Test");
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        order.setMember(member);

        entityManager.persist(order);
        entityTransaction.commit();
        entityManager.clear();

        Order foundOrder = entityManager.find(Order.class, order.getUuid());

        assertThat(foundOrder).usingRecursiveComparison().isEqualTo(order);
        assertThat(foundOrder.getMember()).usingRecursiveComparison().isEqualTo(member);
    }

    @Test
    void item_insert(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Item item = new Item();
        item.setPrice(10000);
        item.setStockQuantity(20);

        entityManager.persist(item);
        entityTransaction.commit();
        entityManager.clear();

        Item foundItem = entityManager.find(Item.class, item.getId());

        assertThat(foundItem).usingRecursiveComparison().isEqualTo(item);
    }

    @Test
    void orderItem_insert(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Member member = new Member();
        member.setName("tester");
        member.setAge(20);
        member.setAddress("Address Test");
        member.setNickName("nick.tester");
        member.setDescription("Description Test");

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("Memo Test");
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        order.setMember(member);

        entityManager.persist(order);

        Item item = new Item();
        item.setPrice(10000);
        item.setStockQuantity(20);

        entityManager.persist(item);

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(2);
        orderItem.setPrice(orderItem.getQuantity()*orderItem.getItem().getPrice());
        orderItem.setOrder(order);

        entityManager.persist(orderItem);
        entityTransaction.commit();
        entityManager.clear();

        OrderItem foundOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        assertThat(foundOrderItem).usingRecursiveComparison().isEqualTo(orderItem);
    }

}
