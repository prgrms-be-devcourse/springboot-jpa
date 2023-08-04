package com.kdt.module.customer.domain.repository;

import com.kdt.module.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class PersistCustomerTest {
    @Autowired
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    private Customer customer = new Customer("hejow", "moon");

    @BeforeEach
    void initManager() {
        entityManager = factory.createEntityManager();
    }

    @Test
    @DisplayName("엔티티를 저장하고 바로 조회하면 DB를 거치지 않아야 한다.")
    void findCustomer_Success_FromPersistenceContext() {
        // given
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // when
        entityManager.persist(customer);
        transaction.commit();

        // then
        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("엔티티를 저장하고 clear한 뒤 찾으면 DB에서 찾아와야 한다.")
    void findCustomer_Success_FromDatabase() {
        // given
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();
        entityManager.clear();

        // when
        Customer findCustomer = entityManager.find(Customer.class, customer.getId());

        // then
        assertThat(entityManager.contains(findCustomer)).isTrue();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Nested
    @DisplayName("영속성 컨텍스트 생명주기 삭제 테스트")
    class deleteCustomerTest {
        @Test
        @DisplayName("엔티티를 저장하고 바로 삭제하면 조회 쿼리가 발생하지 않아야 한다.")
        void deleteCustomer_Success_FromPersistenceContext() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(customer);

            transaction.commit();
            transaction.begin();

            // when
            entityManager.remove(customer);
            transaction.commit();

            // then
            assertThat(entityManager.contains(customer)).isFalse();
        }

        @Test
        @DisplayName("엔티티를 저장하고 clear한 뒤 삭제하려고 하면 IllegalArgumentException이 발생해야 한다.")
        void deleteCustomer_Fail_ByNotExistInPersistenceContext() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(customer);

            transaction.commit();
            entityManager.clear();

            // when, then
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> entityManager.remove(customer));
        }
    }
}
