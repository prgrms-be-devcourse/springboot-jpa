package com.example.springbootjpa;

import com.example.springbootjpa.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersistenceContextTest {

    @Autowired
    private final EntityManagerFactory emf;

    @Autowired
    public PersistenceContextTest(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private Customer createCustomer(Long id, String firstName, String lastName) {
        return new Customer(id, firstName, lastName);
    }

    @Test
    @DisplayName("영속 상태")
    void managed() {

        // given
        EntityManager entityManager = emf.createEntityManager();
        Customer customer = createCustomer(1L, "taehyun", "kong");

        // when
        entityManager.persist(customer);

        // then
        assertThat(entityManager.contains(customer)).isTrue();
    }

    @Test
    @DisplayName("영속 상태 -> 준영속 상태")
    void detached() {

        // given
        EntityManager entityManager = emf.createEntityManager();
        Customer customer = createCustomer(1L, "taehyun", "kong");
        entityManager.persist(customer);

        // when
        entityManager.detach(customer);

        // then
        assertThat(entityManager.contains(customer)).isFalse();
    }

    @Test
    @DisplayName("영속성 컨텍스트 초기화")
    void clear() {

        // given
        EntityManager entityManager = emf.createEntityManager();
        Customer customer1 = createCustomer(1L, "taehyun", "kong");
        Customer customer2 = createCustomer(2L, "sooyung", "Lee");
        entityManager.persist(customer1);
        entityManager.persist(customer2);

        // when
        entityManager.clear();

        // then
        assertThat(entityManager.contains(customer1)).isFalse();
        assertThat(entityManager.contains(customer2)).isFalse();
    }

    @Test
    @DisplayName("준영속 상태 -> 영속 상태")
    void detachedToManaged() {

        // given
        EntityManager entityManager = emf.createEntityManager();
        Customer customer = createCustomer(1L, "taehyun", "kong");
        entityManager.persist(customer);
        entityManager.detach(customer);

        // when
        Customer mergedCustomer = entityManager.merge(customer);

        // then
        assertThat(entityManager.contains(mergedCustomer)).isTrue();
    }

    @Test
    @DisplayName("영속 상태 엔티티 삭제")
    void removeEntity() {

        // given
        EntityManager entityManager = emf.createEntityManager();
        Customer customer = createCustomer(1L, "taehyun", "kong");
        entityManager.persist(customer);

        // when
        entityManager.remove(customer);

        // then
        assertThat(entityManager.contains(customer)).isFalse();
    }
}
