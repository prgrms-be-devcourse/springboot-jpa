package com.example.jpalecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp (){
        log.info("setUp Start.................");
        customerRepository.deleteAll();
        log.info("setUp End...................");
    }

    @Test
    void 저장() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");
        em.persist(customer);

        transaction.commit();

    }

    @Test
    void 조회_DB조회() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");
        em.persist(customer);
        transaction.commit();

        em.detach(customer);

        Customer selected = em.find(Customer.class, 1L);

        assertThat(selected).usingRecursiveComparison().isEqualTo(customer);

    }

    @Test
    void 조회_1차캐시() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");
        em.persist(customer);

        transaction.commit();
        Customer selected = em.find(Customer.class, 1L);

        assertThat(selected).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        Customer selected = em.find(Customer.class, 1L);
        selected.setFirstName("guppy");
        selected.setLastName("hong");
        transaction.commit();

        Customer updated = em.find(Customer.class, 1L);

        assertThat(updated.getFirstName()).isEqualTo("guppy");
        assertThat(updated.getLastName()).isEqualTo("hong");
    }

    @Test
    void 삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        em.remove(customer);
        transaction.commit();
    }
}
