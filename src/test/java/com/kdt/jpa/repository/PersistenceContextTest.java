package com.kdt.jpa.repository;

import com.kdt.jpa.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void create_test() {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("fn1", "ln1");
        entityManager.persist(customer);
        transaction.commit();

        assertThat(customer, is(entityManager.find(Customer.class, customer.getId())));
    }

    @Test
    void find_test_using_db() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("fn2", "ln2");
        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);

        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    void find_test_using_cache() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("fn3", "ln3");
        entityManager.persist(customer);
        transaction.commit();
        
        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    void update_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("fn3", "ln3");
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.changeFirstName("zzz");
        transaction.commit();

        assertThat(customer, samePropertyValuesAs(entityManager.find(Customer.class, customer.getId())));
    }
}
