package org.programmers.kdt.studyjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Slf4j
@SpringBootTest
class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void save() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    void findInDB() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);

        var selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void findInCash() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        entityManager.persist(customer);
        transaction.commit();

        var selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void update() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName("guppy");
        customer.setLastName("hong");

        transaction.commit();
    }

    @Test
    void delete() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();
    }
}
