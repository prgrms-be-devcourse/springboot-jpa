package com.kdt.lecture.repository;

import com.kdt.lecture.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class PersistenceContextTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    private Customer createPersistCustomer(EntityManager entityManager, EntityTransaction transaction) {
        transaction.begin();
        Customer customer = new Customer();
//        customer.setId(1L);
        customer.setFirstName("yongcheol");
        customer.setLastName("kim");
        entityManager.persist(customer);
        transaction.commit();

        return customer;
    }

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("저장")
    void saveTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Customer customer = createPersistCustomer(entityManager, transaction);
        System.out.println(customer.getId());
        assertThat(entityManager.contains(customer)).isTrue();
    }

    @Test
    @DisplayName("조회_DB_이용")
    void findDBDetachTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Customer customer = createPersistCustomer(entityManager, transaction);
        entityManager.detach(customer);
        Customer selected = entityManager.find(Customer.class, customer.getId());

        assertThat(customer).usingRecursiveComparison().isEqualTo(selected);
    }

    @Test
    @DisplayName("조회_1차캐시_이용")
    void findCacheTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Customer customer = createPersistCustomer(entityManager, transaction);
        Customer selected = entityManager.find(Customer.class, customer.getId());

        assertThat(customer).usingRecursiveComparison().isEqualTo(selected);
    }

    @Test
    @DisplayName("수정")
    void updateTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Customer customer = createPersistCustomer(entityManager, transaction);

        transaction.begin();
        customer.setFirstName("yongc");
        customer.setLastName("k");
        transaction.commit();

        assertThat(customer.getFirstName()).isEqualTo("yongc");
        assertThat(customer.getLastName()).isEqualTo("k");

    }

    @Test
    @DisplayName("삭제")
    void deleteTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Customer customer = createPersistCustomer(entityManager, transaction);

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();

        assertThat(entityManager.contains(customer)).isFalse();
    }
}
