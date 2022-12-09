package com.example.springbootjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    EntityManagerFactory emf;

    private final String updateQuery = "";

    private Customer createCustomer() {
        return new Customer(1L, "Kong", "TH");
    }

    @Test
    void save_customer() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = createCustomer();

        // when
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        em.clear();
        transaction.begin();
        Customer findCustomer = em.find(Customer.class, customer.getId());
        em.close();

        // then
        assertThat(customer)
                .usingRecursiveComparison()
                .isEqualTo(findCustomer);
    }

    @Test
    void find_customer() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Customer customer = createCustomer();
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        em.clear();

        // when
        transaction.begin();
        Customer findCustomer = em.find(Customer.class, customer.getId());

        // then
        assertThat(customer)
                .usingRecursiveComparison()
                .isEqualTo(findCustomer);
    }

    @Test
    void delete_customer() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = createCustomer();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // when
        em.remove(customer);
        Customer findCustomer = em.find(Customer.class, customer.getId());

        // then
        assertThat(findCustomer).isNull();
    }

    @Test
    void update_customer() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = createCustomer();
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        em.clear();

        String changeName = "changeName";

        // when
        transaction.begin();
        Customer findCustomer = em.find(Customer.class, customer.getId());
        findCustomer.setFirstName(changeName);
        transaction.commit();
        em.clear();
        Customer findChangeCustomer = em.find(Customer.class, customer.getId());
        // then

        assertThat(findChangeCustomer.getFirstName()).isEqualTo(changeName);
    }
}