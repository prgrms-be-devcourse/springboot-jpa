package com.example.jpademo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PersistenceContextTest {
    @Autowired
    CustomerRepository repository;
    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void 저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jinuk");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    void DB조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jinuk");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);
        Customer selected = entityManager.find(Customer.class, 1L);
        assertThat(selected.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(selected.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void 일차캐시_조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jinuk");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        Customer selected = entityManager.find(Customer.class, 1L);
    }

    @Test
    void 수정() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jinuk");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        customer.setFirstName("진욱");
        customer.setLastName("김");
        Customer selected = entityManager.find(Customer.class, 1L);
        assertThat(selected.getFirstName()).isEqualTo(customer.getFirstName()); // 영속성 컨텍스트 내에 있다면 해당 값을 가져옴
        Customer selectedInDB = repository.findById(1L).get();
        assertThat(selected.getFirstName()).isNotEqualTo(selectedInDB.getFirstName()); // 커밋 전에는 실제 레포지토리와는 다른 값

        transaction.commit();

        selectedInDB = repository.findById(1L).get();
        assertThat(selected.getFirstName()).isEqualTo(selectedInDB.getFirstName()); // 커밋 후에는 실제 레포지토리와 같은 값
    }

    @Test
    void 제거() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jinuk");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        entityManager.remove(customer);
        Optional<Customer> selectedInDB = repository.findById(1L);
        Optional<Customer> removed = Optional.ofNullable(entityManager.find(Customer.class, 1L));
        assertThat(removed.isEmpty()).isTrue();
        assertThat(selectedInDB.isEmpty()).isFalse(); // 커밋 전에는 DB에는 존재

        transaction.commit();

        selectedInDB = repository.findById(1L);
        assertThat(selectedInDB.isEmpty()).isTrue(); // 커밋 후에는 DB에서도 제거
    }
}
