package com.programmers.jpa.order.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderTest {

    @Autowired
    EntityManagerFactory emf;

    @DisplayName("주문을 생성할 수 있다.")
    @Test
    void createOrder() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Order order = new Order(OrderStatus.SUCCESS, "메모");
        em.persist(order);
        transaction.commit();

        //when
        Order foundOorder = em.find(Order.class, order.getId());

        //then
        assertThat(foundOorder).isEqualTo(order);
    }
}
