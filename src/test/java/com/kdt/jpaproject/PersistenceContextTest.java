package com.kdt.jpaproject;

import com.kdt.jpaproject.domain.Customer;
import com.kdt.jpaproject.domain.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        repository.deleteAllInBatch();
    }

    @Test
    public void 저장() throws Exception {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();

        // when
        entityManager.persist(customer); // 비영속 -> 영속
        transaction.commit();

        // then
        assertEquals(repository.count(), 1);
    }

    @Test
    public void 조회_DB조회() throws Exception {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();

        // when
        entityManager.persist(customer); // 비영속 -> 영속
        transaction.commit();

        entityManager.detach(customer); // 영속 -> 준영속
        Customer selected = entityManager.find(Customer.class, 1L);

        // then
        assertAll("customer",
                () -> assertEquals(1L, selected.getId()),
                () -> assertEquals("Lee", selected.getFirstName()),
                () -> assertEquals("YongHoon", selected.getLastName())
        );
    }

    @Test
    public void 조회_1차캐시_이용() throws Exception {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();

        // when
        entityManager.persist(customer); // 비영속 -> 영속
        transaction.commit();
        Customer selected = entityManager.find(Customer.class, 1L);

        // then
        assertEquals(customer, selected);
    }

    @Test
    public void 수정() throws Exception {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();

        // when
        entityManager.persist(customer); // 비영속 -> 영속
        transaction.commit();

        transaction.begin();
        customer.changeLastName("Yooog");
        customer.changeFirstName("dqwdqq");
        Customer selected = entityManager.find(Customer.class, 1L);
        transaction.commit();

        // then
        assertAll("customer",
                () -> assertEquals(1L, selected.getId()),
                () -> assertEquals("dqwdqq", selected.getFirstName()),
                () -> assertEquals("Yooog", selected.getLastName())
        );
    }


    @Test
    public void 삭제() throws Exception {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();

        // when
        entityManager.persist(customer); // 비영속 -> 영속
        transaction.commit();

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();

        // then
        assertEquals(0, repository.count());
    }
}
