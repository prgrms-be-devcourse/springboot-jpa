package com.pgms.jpa.mission2;

import com.pgms.jpa.domain.customer.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersistenceContextLifeCycleTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    // 영속 상태
    @Test
    @DisplayName("객체가 영속 상태가 된다.")
    void persistTest() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("앨런", 30);

        // when
        entityManager.persist(customer);
        boolean isContainsCustomer = entityManager.contains(customer);

        // then
        Assertions.assertThat(isContainsCustomer).isTrue();
    }

    // 비영속 상태
    @Test
    @DisplayName("객체가 비영속 상태가 된다.")
    void nonPersistTest() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("앨런", 30);

        // when
        boolean isContainsCustomer = entityManager.contains(customer);

        // then
        Assertions.assertThat(isContainsCustomer).isFalse();
    }

    // 준영속 상태
    @Test
    @DisplayName("객체가 준영속 상태가 된다. -detach")
    void detachedTest(){
        //given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("앨런", 30);
        entityManager.persist(customer);
        entityManager.flush();
        transaction.commit();

        entityManager.detach(customer);

        // when
        boolean isContainsCustomer = entityManager.contains(customer);

        // then
        Assertions.assertThat(isContainsCustomer).isFalse();
    }

    @Test
    @DisplayName("객체가 준영속 상태가 된다. -remove")
    void persistenceRemoveTest(){
        //given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("앨런", 30);
        entityManager.persist(customer);

        entityManager.remove(customer);
        transaction.commit();

        // when
        boolean isContainsCustomer = entityManager.contains(customer);

        // then
        Assertions.assertThat(isContainsCustomer).isFalse();
    }

    @Test
    @DisplayName("객체가 준영속 상태가 된다. -clear")
    void persistenceClearTest(){
        //given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("앨런", 30);
        entityManager.persist(customer);
        transaction.commit();

        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        entityManager.clear();

        // when
        boolean isContainsCustomer = entityManager.contains(findCustomer);

        // then
        Assertions.assertThat(isContainsCustomer).isFalse();
    }
}
