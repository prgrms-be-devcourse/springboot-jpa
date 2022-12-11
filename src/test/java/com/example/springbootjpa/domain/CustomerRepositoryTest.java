package com.example.springbootjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CustomerRepositoryTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void clear() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        customerRepository.deleteAll();
        transaction.commit();
    }

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
        Customer findCustomer = em.find(Customer.class, customer.getId());

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
        Customer findCustomer = em.find(Customer.class, customer.getId());

        // then
        assertThat(customer)
                .usingRecursiveComparison()
                .isEqualTo(findCustomer);
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
        Customer findCustomer = em.find(Customer.class, customer.getId());
        transaction.begin();
        findCustomer.changeFirstName(changeName);
        transaction.commit();
        em.clear();
        Customer findChangeCustomer = em.find(Customer.class, customer.getId());

        // then
        assertThat(findChangeCustomer.getFirstName()).isEqualTo(changeName);
    }

    @Test
    void delete_customer() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = createCustomer();

        transaction.begin();
        em.persist(customer);

        // when
        em.remove(customer);
        Customer findCustomer = em.find(Customer.class, customer.getId());
        transaction.commit();

        // then
        assertThat(findCustomer).isNull();
    }
}