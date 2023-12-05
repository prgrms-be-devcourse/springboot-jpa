package org.programmers.springjpa.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.springjpa.domain.entity.Customer;
import org.programmers.springjpa.domain.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersistenceContextTest {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private EntityTransaction transaction;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Jiin");
        customer.setLastName("Hong");
    }

    @Test
    void save() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    void find() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);
        Customer expected = entityManager.find(Customer.class, 1L);
        log.info("find customer's firstName '{}', lastName '{}'", expected.getFirstName(), expected.getLastName());
    }

    @Test
    void findWithCache() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        Customer expected = entityManager.find(Customer.class, 1L);
        log.info("find customer's firstName '{}', lastName '{}'", expected.getFirstName(), expected.getLastName());
    }

    @Test
    void update() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setLastName("Park");
        transaction.commit();
    }

    @Test
    void delete() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();
    }
}
