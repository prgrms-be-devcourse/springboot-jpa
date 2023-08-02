package com.programmers.springbootjpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springbootjpa.domain.Address;
import com.programmers.springbootjpa.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @Test
    @DisplayName("고객을 영속 상태로 만들 수 있다.")
    void persistCustomer() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = Customer.builder()
                .name("이현호")
                .nickName("황창현")
                .age(27)
                .address(new Address("서울특별시 영등포구 도신로", "XX빌딩", 10000))
                .build();

        // when
        entityManager.persist(customer);
        entityTransaction.commit();

        Customer persistedCustomer = entityManager.find(Customer.class, 1L);

        // then
        assertThat(persistedCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("고객을 준영속 상태로 만들 수 있다.")
    void semiPersistCustomer() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = Customer.builder()
                .name("이현호")
                .nickName("황창현")
                .age(27)
                .address(new Address("서울특별시 영등포구 도신로", "XX빌딩", 10000))
                .build();

        // when
        entityManager.persist(customer);
        entityTransaction.commit();

        entityManager.detach(customer);
        boolean isSemiPersisted = entityManager.contains(customer);

        // then
        assertThat(isSemiPersisted).isFalse();
    }

    @Test
    @DisplayName("고객을 비영속 상태로 만들 수 있다.")
    void nonPersistCustomer() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = Customer.builder()
                .name("이현호")
                .nickName("황창현")
                .age(27)
                .address(new Address("서울특별시 영등포구 도신로", "XX빌딩", 10000))
                .build();

        // when
        entityTransaction.commit();

        boolean isNonPersisted = entityManager.contains(customer);

        // then
        assertThat(isNonPersisted).isFalse();
    }

    @Test
    @DisplayName("고객을 영속성 컨텍스트에서 삭제할 수 있다.")
    void removeCustomerFromPersistenceContext() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = Customer.builder()
                .name("이현호")
                .nickName("황창현")
                .age(27)
                .address(new Address("서울특별시 영등포구 도신로", "XX빌딩", 10000))
                .build();

        // when
        entityManager.persist(customer);
        entityTransaction.commit();
        entityManager.remove(customer);

        boolean isRemovedFromPersistenceContext = entityManager.contains(customer);
        Customer actualCustomer = entityManager.find(Customer.class, 1L);

        // then
        assertThat(isRemovedFromPersistenceContext).isFalse();
        assertThat(actualCustomer).isNull();
    }

}
