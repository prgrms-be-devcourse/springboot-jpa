package com.kdt.jpaproject.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

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
    void 저장() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();  // 비영속상태
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");

        entityManager.persist(customer);     // 비영속 -> 영속 (영속화)
        transaction.commit();               // entityManager.flush();
    }

    @Test
    void 조회_DB조회_detach() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);     // 영속 -> 준영속

        Customer selectedCustomer = entityManager.find(Customer.class, 1L);     // select
        log.info("{} {}", selectedCustomer.getFirstName(), selectedCustomer.getFirstName());

        assertThat(selectedCustomer)
                .hasFieldOrPropertyWithValue("id", customer.getId())
                .hasFieldOrPropertyWithValue("firstName", customer.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", customer.getLastName());
    }

    @Test
    void 조회_DB조회_clear() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.clear();

        Customer selectedCustomer = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selectedCustomer.getFirstName(), selectedCustomer.getFirstName());
        assertThat(selectedCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 조회_DB조회_close() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.close();

//        Customer selectedCustomer = entityManager.find(Customer.class, 1L);       // EntityManager is closed
//        log.info("{} {}", selectedCustomer.getFirstName(), selectedCustomer.getFirstName());
    }

    @Test
    void 조회_DB조회_merge() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");
        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);
        Customer merge = entityManager.merge(customer);

        Customer mergedCustomer = entityManager.find(Customer.class, 1L);
        log.info("{} {}", mergedCustomer.getFirstName(), mergedCustomer.getFirstName());
        assertThat(mergedCustomer).usingRecursiveComparison().isEqualTo(merge);
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");
        entityManager.persist(customer);
        transaction.commit();

        Customer selectedCustomer = entityManager.find(Customer.class, 1L);     // 1차 캐시
        log.info("{} {}", selectedCustomer.getFirstName(), selectedCustomer.getFirstName());
        assertThat(selectedCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 수정() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName("Hong");
        customer.setLastName("guppy");
        transaction.commit();               // update

        Customer updatedCustomer = entityManager.find(Customer.class, 1L);
        log.info("{} {}", updatedCustomer.getFirstName(), updatedCustomer.getLastName());
        assertThat(updatedCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 삭제() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        entityManager.remove(customer);     // 영속 -> 비영속 (DB삭제)
        transaction.commit();               // delete

        assertThat(entityManager.find(Customer.class, 1L)).isNull();
    }
}
