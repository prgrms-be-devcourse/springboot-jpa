package com.programmers.springbootjpa.domain;

import com.programmers.springbootjpa.domain.order.Item;
import com.programmers.springbootjpa.domain.order.Member;
import com.programmers.springbootjpa.domain.order.Order;
import com.programmers.springbootjpa.domain.order.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.UUID;

import static com.programmers.springbootjpa.domain.order.OrderStatus.OPENED;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@DataJpaTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("Member - Order의 연관관계 테스트")
    void memberAndOrderAssociationMappingTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member("Hyeonseo Jung",
                "HS",
                24,
                "Mapo-gu, Seoul",
                "description test");
        entityManager.persist(member);

        Order firstOrder = new Order(UUID.randomUUID().toString(),
                "order No.1",
                OPENED);
        Order secondOrder = new Order(UUID.randomUUID().toString(),
                "order No.2",
                OPENED);
        firstOrder.setMember(member);
        secondOrder.setMember(member);
        entityManager.persist(firstOrder);
        entityManager.persist(secondOrder);

        transaction.commit();

        entityManager.clear();

        Order foundFirstOrder = entityManager.find(Order.class, firstOrder.getUuid());
        Order foundSecondOrder = entityManager.find(Order.class, secondOrder.getUuid());
        Member foundMember = entityManager.find(Member.class, member.getId());

        assertThat(foundFirstOrder.getMember(), samePropertyValuesAs(foundMember));
        assertThat(foundSecondOrder.getMember(), samePropertyValuesAs(foundMember));
    }

    @Test
    @DisplayName("Order - OrderItem의 연관관계 테스트")
    void OrderAndOrderItemAssociationMappingTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order(UUID.randomUUID().toString(),
                "order No.1",
                OPENED);
        entityManager.persist(order);

        OrderItem firstOrderItem = new OrderItem(5000, 3);
        OrderItem secondOrderItem = new OrderItem(10000, 1);
        firstOrderItem.setOrder(order);
        secondOrderItem.setOrder(order);

        entityManager.persist(firstOrderItem);
        entityManager.persist(secondOrderItem);

        transaction.commit();

        entityManager.clear();

        Order foundOrder = entityManager.find(Order.class, order.getUuid());
        OrderItem foundFirstOrderItem = entityManager.find(OrderItem.class, firstOrderItem.getId());
        OrderItem foundSecondOrderItem = entityManager.find(OrderItem.class, secondOrderItem.getId());

        assertThat(foundFirstOrderItem.getOrder(), samePropertyValuesAs(foundOrder));
        assertThat(foundSecondOrderItem.getOrder(), samePropertyValuesAs(foundOrder));
    }

    @Test
    @DisplayName("OrderItem - Item의 연관관계 테스트")
    void OrderItemAndItemAssociationMappingTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Item item = new Item(5000, 10);
        entityManager.persist(item);

        OrderItem firstOrderItem = new OrderItem(15000, 3);
        OrderItem secondOrderItem = new OrderItem(5000, 1);
        firstOrderItem.setItem(item);
        secondOrderItem.setItem(item);

        entityManager.persist(firstOrderItem);
        entityManager.persist(secondOrderItem);

        transaction.commit();

        entityManager.clear();

        Item foundItem = entityManager.find(Item.class, item.getId());
        OrderItem foundFirstOrderItem = entityManager.find(OrderItem.class, firstOrderItem.getId());
        OrderItem foundSecondOrderItem = entityManager.find(OrderItem.class, secondOrderItem.getId());

        assertThat(foundFirstOrderItem.getItem(), samePropertyValuesAs(foundItem));
        assertThat(foundSecondOrderItem.getItem(), samePropertyValuesAs(foundItem));
    }

}
