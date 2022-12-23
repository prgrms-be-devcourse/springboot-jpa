package com.prgrms.springbootjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("비영속 엔티티를 영속화 할 수 있다.")
    void testPersistenceContextWithSave() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = createCustomer();   // 비영속 상태

        entityManager.persist(customer);        // 비영속 -> 영속 (영속화)

        transaction.commit();                   // flush()
    }

    @Test
    @DisplayName("저장된 엔티티를 영속성 컨텍스트로 가져올 수 있다.")
    void testPersistenceContextWithFind() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = createCustomer();   // 비영속 상태

        entityManager.persist(customer);        // 비영속 -> 영속 (영속화)

        transaction.commit();                   // flush()

        entityManager.clear();                  // 영속성 컨텍스트 초기화

        // clear()를 하지 않고 find()를 할 경우 1차 캐시에서 조회한다.
        Customer selected = entityManager.find(Customer.class, 1L);
        assertThat(customer, samePropertyValuesAs(selected));
    }

    @Test
    @DisplayName("1차 캐시에 있는 엔티티는 변경 감지로 인해 수정된다.")
    void testPersistenceContextWithUpdate() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = createCustomer();   // 비영속 상태

        entityManager.persist(customer);        // 비영속 -> 영속 (영속화)

        transaction.commit();                   // flush()

        transaction.begin();

        customer.changeFirstName("Park");
        customer.changeLastName("David");

        transaction.commit();                   // Customer(id = 1L)의 변경 감지 -> Update Query

        entityManager.clear();

        Customer updated = entityManager.find(Customer.class, 1L);
        assertThat(customer, samePropertyValuesAs(updated));
    }

    @Test
    @DisplayName("영속성 컨텍스트에서 삭제한 엔티티는 DB에서도 찾을 수 없다.")
    void testPersistenceContextWithDelete() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = createCustomer();   // 비영속 상태

        entityManager.persist(customer);        // 비영속 -> 영속 (영속화)

        transaction.commit();                   // flush()

        transaction.begin();

        entityManager.remove(customer);         // 영속성 컨테이너 내에서 삭제

        transaction.commit();                   // 변경 감지

        entityManager.clear();

        Customer updated = entityManager.find(Customer.class, 1L);
        assertThat(updated, is(nullValue(Customer.class)));
    }

    private Customer createCustomer() {
        return Customer.builder()
                .id(1L)
                .firstName("Kim")
                .lastName("Changgyu")
                .build();
    }
}
