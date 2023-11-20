package com.devcourse.springbootjpaweekly.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CustomerTest {

    static final String DELETE_FROM_CUSTOMER = "DELETE FROM customer";

    @Autowired
    EntityManagerFactory emf;
    EntityManager entityManager;

    @BeforeEach
    void setup() {
        entityManager = emf.createEntityManager();
    }

    @AfterEach
    void cleanup() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.createNativeQuery(DELETE_FROM_CUSTOMER)
                .executeUpdate();

        transaction.commit();

        entityManager.close();
    }

    @DisplayName("빌더로 생성할 수 있다.")
    @Test
    void testCreate() {
        // given
        EntityTransaction transaction = entityManager.getTransaction();

        // when
        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("name")
                .lastName("kim")
                .email("kim@kim.kim")
                .build();

        entityManager.persist(customer);

        transaction.commit();

        entityManager.clear();

        // then
        assertThat(customer).hasNoNullFieldsOrPropertiesExcept("orders");

        Customer actual = entityManager.find(Customer.class, customer.getId());

        assertThat(actual).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(customer);
    }
}
