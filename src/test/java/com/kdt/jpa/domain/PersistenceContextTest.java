package com.kdt.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    private EntityManager entityManager;
    private EntityTransaction transaction;
    private Customer customer;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("beomseok");
        customer.setLastName("ko");
    }

    @Test
    @DisplayName("영속 테스트")
    void save() {

        // Given

        // When
        transaction.begin();
        /**
         * commit() => entityManager.flush()
         *         쓰기 지연에 있던 쿼리들 수행..
         *         쓰기 지연 : Entity를 persist 한다고 해서 데이터베이스에 쿼리가 바로 전송되는 것이 아니다.
         *          * commit 이 되는 순간 (트랜잭션이 종료되는 시점) 쿼리가 전송
         *         쿼리를 늦게 한번에 보낸다.
         */
        entityManager.persist(customer);
        transaction.commit();

        // Then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }


    @Test
    @DisplayName("DB에 직접 조회")
    void testFindByDB() {
        // Given

        // When
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();

        entityManager.detach(customer); // 영속성 컨텍스트에서 제외

        // Then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        log.info("{}, {}", findCustomer.getFirstName(), findCustomer.getLastName());
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("1차 캐시를 통해 조회")
    void testFindByCache() {
        // Given

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setLastName("ko");
        customer2.setFirstName("beomsic");

        // When
        repository.save(customer2);
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();

        // Then

        Customer findCustomer = entityManager.find(Customer.class, 1L); // 1차 캐시에서 조회
        assertThat(findCustomer, samePropertyValuesAs(customer));

        /**
         * 1차 캐시에 존재하지 않는 엔티티 조회할 경우
         * - 1차 캐시에서 식별자로 가진 엔티티 조회
         * - 1차 캐시에 존재하지 않기 때문에 DB에 조회
         * - 1차 캐시에 저장
         */
        Customer findCustomer2 = entityManager.find(Customer.class, 2L);
        assertThat(findCustomer2.getId() == 2 , is(true));


        /**
         * 영속 엔티티 동일성
         * a 와 b는 1차 캐시에 있는 같은 엔티티 반환 받는다.
         */
        Customer a = entityManager.find(Customer.class, 1L);
        Customer b = entityManager.find(Customer.class, 1L);
        assertThat(a == b ,is(true));
    }

    @Test
    @DisplayName("업데이트 테스트")
    void testUpdate() {
        // Given

        // When
        transaction.begin();

        entityManager.persist(customer);

        customer.setLastName("go");

        transaction.commit();

        // Then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        assertThat(findCustomer.getLastName().equals("go"), is(true));
    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() {
        // Given

        // When
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        assertThat(entityManager.contains(customer), is(true));

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();

        // Then
        assertThat(entityManager.contains(customer), is(false));

    }

    @Test
    @DisplayName("동일성 테스트")
    void testPersistence() {
        // Given

        // When
        transaction.begin();

        entityManager.persist(customer); // 영속성
        Customer customer1 = entityManager.find(Customer.class, 1L);
        customer1.setFirstName("test");
        Customer customer2 = entityManager.find(Customer.class, 1L);

        // Then
        assertThat(customer1, samePropertyValuesAs(customer2));
        // 1차캐시에 있는 값이 바로 변경?

        customer2.setFirstName("beomseok");
        assertThat(customer2, samePropertyValuesAs(customer1));

        transaction.rollback();

    }
}
