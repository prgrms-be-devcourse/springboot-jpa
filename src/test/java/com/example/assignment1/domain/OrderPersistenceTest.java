package com.example.assignment1.domain;

import com.example.assignment1.domain.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("주문을 저장하는 경우 주문 상품도 같이 저장된다.")
    public void insertOrderTest() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Item item = new Item("EGG", 200, 30);
        Member customer = new Member("Yoonoh", "UNO", 25, "서울시 광진구 능동로 120");

        // when
        transaction.begin();
        entityManager.persist(customer);
        entityManager.persist(item);
        transaction.commit();

        transaction.begin();
        Order order = new Order(customer, "문앞에 두고 문자주세요");
        OrderItem orderItem = new OrderItem(order, item, item.getPrice(), 10);
        order.addOrderItem(orderItem);
        entityManager.persist(order); // order만 persist
        transaction.commit();

        // then
        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        assertThat(findOrderItem, is(notNullValue()));
        assertThat(orderItem, samePropertyValuesAs(findOrderItem));
    }

    @Test
    @DisplayName("주문을 제거하는 경우 주문 상품도 같이 삭제된다.")
    public void deleteOrderTest() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Item item = new Item("EGG", 200, 30);
        Member customer = new Member("Yoonoh", "UNO", 25, "서울시 광진구 능동로 120");

        // when
        transaction.begin();
        entityManager.persist(customer);
        entityManager.persist(item);
        transaction.commit();

        transaction.begin();
        Order order = new Order(customer, "문앞에 두고 문자주세요");
        OrderItem orderItem = new OrderItem(order, item, item.getPrice(), 10);
        order.addOrderItem(orderItem);
        entityManager.persist(order);
        transaction.commit();

        entityManager.remove(order);

        // then
        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        assertThat(findOrderItem, is(nullValue()));
    }
}
