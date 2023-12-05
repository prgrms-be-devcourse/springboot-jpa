package com.programmers.springbootjpa.domain;

import com.programmers.springbootjpa.domain.entity.Customer;
import com.programmers.springbootjpa.domain.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Entity 생명주기 테스트")
class PersistenceContextTest {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    private EntityManager entityManager;
    private EntityTransaction transaction;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("David");
        customer.setLastName("Lee");
    }


    @Test
    @DisplayName("저장")
    void testSave() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    @DisplayName("조회: DB 조회")
    void testFind() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();
        entityManager.detach(customer);
        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    @DisplayName("조회: 1차 캐시 이용")
    void testFindWithCache() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();
        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    @DisplayName("수정")
    void testUpdate() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName("David");
        customer.setLastName("Lee");

        transaction.commit();
    }

    @Test
    @DisplayName("삭제")
    void testDelete() {
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();
    }
}
