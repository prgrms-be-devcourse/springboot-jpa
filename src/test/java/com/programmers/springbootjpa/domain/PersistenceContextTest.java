package com.programmers.springbootjpa.domain;

import com.programmers.springbootjpa.domain.customer.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class PersistenceContextTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager entityManager;

    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @DisplayName("영속화 테스트")
    @Test
    void testPersistence() {
        //given
        transaction.begin();

        Customer customer = new Customer("hyemin", "Kim");

        //when
        entityManager.persist(customer);

        //then
        assertThat(entityManager.contains(customer)).isTrue();

        transaction.commit();
    }

    @DisplayName("1차 캐시를 이용한 조회 테스트")
    @Test
    void testUsingCache() {
        //given
        transaction.begin();

        Customer customer = new Customer("hyemin", "Kim");

        entityManager.persist(customer);
        transaction.commit();

        //when
        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

        //then
        assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
        assertThat(foundCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(foundCustomer.getLastName()).isEqualTo(customer.getLastName());
    }

    @DisplayName("DB를 이용한 조회 테스트")
    @Test
    void testUsingDb() {
        //given
        transaction.begin();

        Customer customer = new Customer("hyemin", "Kim");

        entityManager.persist(customer);
        transaction.commit();

        //when
        entityManager.detach(customer);

        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

        //then
        assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
        assertThat(foundCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(foundCustomer.getLastName()).isEqualTo(customer.getLastName());
    }

    @DisplayName("수정 테스트")
    @Test
    void testUpdateUsingDirtyChecking() {
        //given
        transaction.begin();

        Customer customer = new Customer("hyemin", "Kim");

        entityManager.persist(customer);
        transaction.commit();

        //when
        transaction.begin();

        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

        foundCustomer.updateFirstName("min");
        foundCustomer.updateLastName("Lee");

        transaction.commit();

        Customer result = entityManager.find(Customer.class, customer.getId());

        //then
        assertThat(result.getId()).isEqualTo(foundCustomer.getId());
        assertThat(result.getFirstName()).isEqualTo(foundCustomer.getFirstName());
        assertThat(result.getLastName()).isEqualTo(foundCustomer.getLastName());
    }

    @DisplayName("삭제 테스트")
    @Test
    void testDelete() {
        //given
        transaction.begin();

        Customer customer = new Customer("hyemin", "Kim");

        entityManager.persist(customer);
        transaction.commit();

        //when
        transaction.begin();

        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());
        entityManager.remove(foundCustomer);

        transaction.commit();

        //then
        assertThat(entityManager.contains(foundCustomer)).isFalse();
    }
}
