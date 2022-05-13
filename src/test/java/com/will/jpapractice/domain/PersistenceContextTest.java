package com.will.jpapractice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@DataJpaTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    EntityManager entityManager;

    EntityTransaction transaction;

    Customer customer;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();

        transaction.begin();

        customer = Customer.builder()
                .firstName("will")
                .lastName("kim").build();

        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    void 고객정보_캐시_저장_확인() {
        // Then
        Customer seleted = entityManager.find(Customer.class, customer.getId());
        assertThat(seleted.getId()).isEqualTo(customer.getId());
        assertThat(seleted.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(seleted.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void 고객정보_DB_저장_확인() {
        // When
        entityManager.detach(customer);

        // Then
        Customer seleted = entityManager.find(Customer.class, customer.getId());
        assertThat(seleted.getId()).isEqualTo(customer.getId());
        assertThat(seleted.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(seleted.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void 고객정보_DB_수정_확인() {
        //When
        transaction.begin();

        customer.setFirstName("jake");
        customer.setLastName("Joe");
        transaction.commit();

        entityManager.detach(customer);

        // Then
        Customer seleted = entityManager.find(Customer.class, customer.getId());
        assertThat(seleted.getId()).isEqualTo(customer.getId());
        assertThat(seleted.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(seleted.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void 고객정보_DB_삭제_확인() {
        // When
        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();
        entityManager.detach(customer);

        // Then
        Customer seleted = entityManager.find(Customer.class, customer.getId());
        assertThat(seleted).isNull();
    }
}
