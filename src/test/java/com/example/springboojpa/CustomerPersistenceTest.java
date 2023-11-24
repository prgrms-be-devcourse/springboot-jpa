package com.example.springboojpa;

import com.example.springboojpa.domain.customer.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class CustomerPersistenceTest {

    @Autowired
    private EntityManagerFactory emf;

    private Customer customer;

    @BeforeEach
    void initCustomer() {
        customer = new Customer(1L,"kim","yongsang");
    }


    @Test
    public void save() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);

        transaction.commit();
    }

    @Test
    public void findById() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);
        transaction.commit();

        em.clear();

        Customer entity = em.find(Customer.class, 1L);

        Assertions.assertThat(entity.getId()).isEqualTo(customer.getId());
    }

    @Test
    void find() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        entity.setFirstName("Leonel");
        entity.setLastName("Messi");

        transaction.commit();

        Assertions.assertThat(entity.getFirstName()).isNotEqualTo("kim");
    }

    @Test
    void delete() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        em.remove(entity);

        transaction.commit();

        Customer customer1 = em.find(Customer.class, 1L);
        Assertions.assertThatThrownBy(() -> customer1.getId()).isInstanceOf(NullPointerException.class);
    }
}
