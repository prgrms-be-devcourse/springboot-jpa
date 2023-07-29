package org.programmers.jpaweeklymission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import org.programmers.jpaweeklymission.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersistenceContextTest {
    @Autowired
    private EntityManagerFactory emf;
    private EntityManager em;
    // DataJpaTest 에 이미 트랜잭션 이 있지만 또 트랜잭션을 생성하는 이유?
    // @Transaction 은 Spring AOP 에서 관리하는데 EntityManagerFactory 로부터 EntityManager 를 생성하면
    // AOP 를 통해 생성되지 않았으르므로 Spring 은 그 트랜잭션을 관리할 수 없다.
    // 그래서, Test 시에는 TestEntityManager 를 사용하는 방법이 있다. -> spring 관리 대상
    private EntityTransaction tx;

    private Customer customer;

    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        customer = Customer.builder()
                .firstName("상민")
                .lastName("박")
                .build();
    }

    @AfterEach
    void end() {
        em.close();
    }

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void testSave() {
        // given
        // when
        tx.begin();
        try {
            em.persist(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        // then
        assertThat(customer.getId()).isNotNull();
    }

    @Test
    @DisplayName("고객을 PersistenceContext 에서 조회할 수 있다.")
    void testFindCustomerByIdInPersistenceContext() {
        //given
        tx.begin();
        try {
            em.persist(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        // when
        Customer foundCustomer = em.find(Customer.class, customer.getId());

        // then
        assertThat(foundCustomer).isNotNull();
    }

    @Test
    @DisplayName("고객을 DB 에서 조회할 수 있다.")
    void testFindCustomerByIdInDb() {
        // given
        tx.begin();
        try {
            em.persist(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        em.clear();

        // when
        Customer foundCustomer = em.find(Customer.class, customer.getId());

        // then
        assertThat(foundCustomer).isNotNull();
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    void testUpdate() {
        // given
        tx.begin();
        try {
            em.persist(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        // when
        tx.begin();
        try {
            Customer foundCustomer = em.find(Customer.class, customer.getId());
            foundCustomer.changeFirstName("쟁이");
            foundCustomer.changeLastName("멋");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        // then
        Customer updated = em.find(Customer.class, customer.getId());
        assertThat(updated.getFirstName()).isEqualTo("쟁이");
        assertThat(updated.getLastName()).isEqualTo("멋");
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void testDelete() {
        // given
        tx.begin();
        try {
            em.persist(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        // when
        tx.begin();
        try {
            em.remove(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        // then
        Customer deleted = em.find(Customer.class, customer.getId());
        assertThat(deleted).isNull();
    }
}
