package com.devcourse.springbootjpaweekly.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    EntityManagerFactory emf;
    EntityManager entityManager;

    @BeforeEach
    void setup() {
        entityManager = emf.createEntityManager();
    }

    @DisplayName("빌더로 생성할 수 있다.")
    @Test
    void testCreate() {
        // given
        EntityTransaction transaction = entityManager.getTransaction();

        // when
        transaction.begin();

        Customer customer = Customer.builder()
                .name("kim")
                .email("kim@kim.kim")
                .build();

        entityManager.persist(customer);

        transaction.commit();

        entityManager.clear();

        // then
        assertThat(customer).hasNoNullFieldsOrProperties();

        Customer actual = entityManager.find(Customer.class, customer.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(customer);
    }
}
