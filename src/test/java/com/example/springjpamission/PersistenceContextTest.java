package com.example.springjpamission;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.springjpamission.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class PersistenceContextTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void save() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setFirstName("윤");
        customer.setLastName("영운");

        em.persist(customer);

        transaction.commit();
        em.clear();

        Customer findCustomer = em.find(Customer.class, customer.getId());

        assertThat(findCustomer.getId()).isEqualTo(customer.getId());
    }

    @Test
    void update() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setFirstName("윤");
        customer.setLastName("영운");

        em.persist(customer);

        transaction.commit();

        transaction.begin();

        Customer findCustomer = em.find(Customer.class, customer.getId());
        findCustomer.setLastName("별");
        findCustomer.setFirstName("김");

        em.persist(findCustomer);

        transaction.commit();

        em.clear();
        Customer updatedCustomer = em.find(Customer.class, findCustomer.getId());

        assertThat(updatedCustomer.getFirstName()).isEqualTo("김");
        assertThat(updatedCustomer.getLastName()).isEqualTo("별");
    }

    @Test
    void delete() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setFirstName("윤");
        customer.setLastName("영운");

        em.persist(customer);

        transaction.commit();
        Customer findCustomer = em.find(Customer.class, customer.getId());

        transaction.begin();

        em.remove(findCustomer);

        transaction.commit();

        Customer deletedCustomer = em.find(Customer.class, findCustomer.getId());

        assertThat(deletedCustomer).isNull();

    }
}
