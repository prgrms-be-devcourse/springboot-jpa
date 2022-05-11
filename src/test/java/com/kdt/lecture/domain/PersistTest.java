package com.kdt.lecture.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.contentOf;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PersistTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    Customer customer = new Customer(1L, "hyounguk", "kim"); // 비영속 상태

    @BeforeEach
    void setUp() {
        customerRepository.deleteAllInBatch();
    }

    @Test
    void 영속상태_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        // THEN
        em.persist(customer); // 영속상태
        transaction.commit(); // 쿼리날라가 쓰기지연 플러쉬 발생

        // THEN
        assertThat(em.contains(customer)).isTrue();
    }

    @Test
    void 영속성_1차캐시_조회_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);

        // WHEN
        transaction.commit();

        // THEN
        Customer selected = em.find(Customer.class, 1L); // 쿼리가 실행되지 않는다.
        assertThat(customer).isEqualTo(selected);
    }

    @Test
    void 영속성_DB_조회_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // WHEN
        em.detach(customer);

        // THEN
        Customer selected = em.find(Customer.class, 1L); // 쿼리를 통해 새로 가져온다
        assertThat(customer.getFirstName()).isEqualTo(selected.getFirstName());
    }

    @Test
    void 수정_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // WHEN
        transaction.begin();
        customer.setFirstName("hyoungjoon"); // 더티체킹으로 커밋 시점에 update 쿼리가 실행된다.
        transaction.commit();

        em.detach(customer);

        // THEN
        Customer selected = em.find(Customer.class, 1L);
        assertThat(customer.getFirstName()).isEqualTo(selected.getFirstName());
    }

    @Test
    void 준영속상태_DETACH_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // WHEN
        em.detach(customer);

        // THEN
        assertThat(em.contains(customer)).isFalse();
    }

    @Test
    void 준영속상태_CLEAR_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // WHEN
        em.clear();

        // THEN
        assertThat(em.contains(customer)).isFalse();
    }

    @Test
    void 준영속상태_CLOSE_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // WHEN
        em.close();

        // THEN
        assertThat(em.isOpen()).isFalse();
    }

    @Test
    void 삭제_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // WHEN
        transaction.begin();
        em.remove(customer);
        transaction.commit();

        // THEN
        Customer selected = em.find(Customer.class, 1L);
        assertThat(selected).isNull();
    }

}
