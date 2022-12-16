package com.prgms.springbootjpa.repository;

import com.prgms.springbootjpa.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EntityLifeCycleTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    CustomerRepository customerRepository;

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @Test
    @DisplayName("영속상태에서는 EntityManager가 객체를 가지고 있다.")
    void 영속상태로_만든다() {
        transaction.begin();

        // 비영속 - new, transient 상태
        Customer customer = new Customer("Tina","Jeong");

        // 영속 - managed 상태
        entityManager.persist(customer);
        assertTrue(entityManager.contains(customer));
        assertFalse(customerRepository.findById(1).isPresent());

        // DB에 반영
        transaction.commit();
        assertTrue(entityManager.contains(customer));
        assertTrue(customerRepository.findById(1).isPresent());
    }

    @Test
    @DisplayName("준영속상태는 EntityMangaer가 객체를 가지고 있다가 제거한 상태이다.")
    void 준영속상태로_만든다() {
        transaction.begin();

        // 비영속 - new, transient 상태
        Customer customer = new Customer("Tina","Jeong");

        // 영속 - managed 상태
        entityManager.persist(customer);
        assertTrue(entityManager.contains(customer));
        assertFalse(customerRepository.findById(1).isPresent());

        // DB에 반영
        transaction.commit();
        assertTrue(entityManager.contains(customer));
        assertTrue(customerRepository.findById(1).isPresent());

        // 준영속 - detached 상태
        entityManager.detach(customer);
        assertFalse(entityManager.contains(customer));
        assertTrue(customerRepository.findById(1).isPresent());
    }

    @Test
    @DisplayName("삭제상태에서는 영속성 컨텍스트와도 관련이 없는 상태이며, DB에서 삭제되기로 예정된 상태이다.")
    void 삭제상태로_만든다() {
        transaction.begin();
        // 비영속 - new, transient 상태
        Customer customer = new Customer("Tina","Jeong");

        // 영속 - managed 상태
        entityManager.persist(customer);
        assertTrue(entityManager.contains(customer));
        assertFalse(customerRepository.findById(1).isPresent());

        // DB에 반영
        transaction.commit();
        assertTrue(entityManager.contains(customer));
        assertTrue(customerRepository.findById(1).isPresent());

        // 삭제 - removed 상태 (DB에서 삭제되기로 schedule된 상태)
        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();
        assertFalse(entityManager.contains(customer));
        assertTrue(customerRepository.findById(1).isPresent());
    }

}
