package kr.co.prgrms.jpaintro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import kr.co.prgrms.jpaintro.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersistenceContextTest {
    @Autowired
    private EntityManagerFactory emf;

    @Test
    @DisplayName("영속 상태 테스트")
    void persistTest() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // when
        transaction.begin();
        Customer customer = new Customer("예성", "고");
        em.persist(customer);

        // then
        assertThat(em.contains(customer)).isTrue();
        transaction.commit();
    }

    @Test
    @DisplayName("준영속 상태 테스트")
    void detachTest() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer("예성", "고");
        em.persist(customer);

        // when
        em.detach(customer);

        // then
        assertThat(em.contains(customer)).isFalse();
    }

    @Test
    @DisplayName("클리어 테스트")
    void clearTest() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer1 = new Customer("예성", "고");
        Customer customer2 = new Customer("요송", "고");

        em.persist(customer1);
        em.persist(customer2);

        // when
        em.clear();

        // then
        assertThat(em.contains(customer1)).isFalse();
        assertThat(em.contains(customer2)).isFalse();
    }

    @Test
    @DisplayName("비영속 상태 테스트")
    void removeTest() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer("예성", "고");
        em.persist(customer);

        // when
        em.remove(customer);

        // then
        assertThat(em.contains(customer)).isFalse();
        transaction.commit();
    }

    @Test
    @DisplayName("머지 테스트")
    void mergeTest() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer("예성", "고");
        em.persist(customer);
        em.flush();

        // when
        em.detach(customer);
        Customer mergedCustomer = em.merge(customer);

        // then
        assertThat(em.contains(mergedCustomer)).isTrue();
        transaction.commit();
    }
}
