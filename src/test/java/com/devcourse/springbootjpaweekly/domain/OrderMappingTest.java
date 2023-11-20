package com.devcourse.springbootjpaweekly.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
public class OrderMappingTest {

    static final String DELETE_FROM_CUSTOMER = "DELETE FROM customer";
    static final String DELETE_FROM_ORDER = "DELETE FROM orders";

    @Autowired
    EntityManagerFactory emf;
    EntityManager entityManager;
    EntityTransaction transaction;

    @BeforeEach
    void setup() {
        entityManager = emf.createEntityManager();
    }

    @AfterEach
    void cleanup() {
        transaction.begin();

        entityManager.createNativeQuery(DELETE_FROM_ORDER)
                .executeUpdate();
        entityManager.createNativeQuery(DELETE_FROM_CUSTOMER)
                .executeUpdate();

        transaction.commit();

        entityManager.close();
    }

    @DisplayName("주문 엔티티에서 매핑된 고객을 가져온다.")
    @Test
    void testCustomerMappingTest() {
        // given
        transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("name")
                .lastName("kim")
                .email("kim@kim.kim")
                .build();
        Order order = Order.builder()
                .orderStatus(OrderStatus.OPENED)
                .memo("전자 기기")
                .customer(customer)
                .build();

        entityManager.persist(customer);
        entityManager.persist(order);

        transaction.commit();

        // when
        entityManager.clear();

        Order foundOrder = entityManager.find(Order.class, order.getId());
        Customer actualCustomer = foundOrder.getCustomer();

        // then
        assertThat(actualCustomer).isNotNull()
                .hasFieldOrPropertyWithValue("id", customer.getId())
                .hasFieldOrPropertyWithValue("email", customer.getEmail());
    }

}
