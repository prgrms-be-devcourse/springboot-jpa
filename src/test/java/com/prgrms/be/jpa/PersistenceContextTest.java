package com.prgrms.be.jpa;

import com.prgrms.be.jpa.domain.Customer;
import com.prgrms.be.jpa.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PersistenceContextTest {
    private static final String FIRST = "수영";
    private static final String LAST = "이";
    private static final long ID = 1L;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void init() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("persist를 이용하여 DB에 고객 정보를 저장할 수 있다.")
    void persistence_insert_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);
        transaction.commit();

        // then
        em.clear();
        Customer findCustomer = em.find(Customer.class, ID);
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("1차 캐시에 존재하는 고객의 정보를 가져올 수 있다.")
    void persistence_find_Cache_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);
        transaction.commit();

        //then
        Customer findCustomer = em.find(Customer.class, ID);
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("DB에 저장된 고객 정보를 가져올 수 있다.")
    void persistence_find_DB_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);
        transaction.commit();

        // then
        em.clear();
        Customer findCustomer = em.find(Customer.class, ID);
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("변경감지를 이용하여 엔티티의 수정된 정보를 DB에 업데이트할 수 있다.")
    void persistence_update_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);
        transaction.commit();

        String chgFirstName = "태현";
        String chgLastName = "공";
        transaction.begin();
        customer.changeName(chgFirstName, chgLastName);
        transaction.commit();

        // then
        em.clear();
        Customer updateEntity = em.find(Customer.class, ID);
        assertThat(updateEntity.getFirstName(), is(chgFirstName));
        assertThat(updateEntity.getLastName(), is(chgLastName));
    }

    @Test
    @DisplayName("DB에 존재하는 고객의 정보를 삭제할 수 있다.")
    void persistence_delete_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        em.remove(customer);
        transaction.commit();

        // then
        Customer deleteCustomer = em.find(Customer.class, ID);
        assertThat(Objects.equals(deleteCustomer, null), is(true));
    }

    @Test
    @DisplayName("flush 하지 않은 영속화된 고객의 정보를 바꾼 후 flush를 하면 바뀐 고객의 정보가 flush된다.")
    void persistence_not_flush_update_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);

        String chgFirstName = "태현";
        String chgLastName = "공";
        customer.changeName(chgFirstName, chgLastName);
        transaction.commit();

        // then
        em.clear();
        Customer updateEntity = em.find(Customer.class, ID);
        assertThat(updateEntity.getFirstName(), is(chgFirstName));
        assertThat(updateEntity.getLastName(), is(chgLastName));
    }

    @Test
    @DisplayName("영속화 되지 않은 엔티티의 정보를 수정하고 commit을 하면 DB 정보가 업데이트 되지 않는다.")
    void persistence_not_update_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        String chgFirstName = "태현";
        String chgLastName = "공";
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);
        transaction.commit();
        em.clear();

        transaction.begin();
        customer.changeName(chgFirstName, chgLastName);
        transaction.commit();

        // then
        em.clear();
        Customer updateEntity = em.find(Customer.class, ID);
        assertThat(Objects.equals(updateEntity.getFirstName(), chgFirstName), is(false));
        assertThat(Objects.equals(updateEntity.getLastName(), chgLastName), is(false));
    }

    @Test
    @DisplayName("고객 정보를 수정하고 commit하기 전 준영속화를 시키면 DB 정보가 변경되지 않는다.")
    void persistence_before_update_unpersist_test() {
        // given
        Customer customer = new Customer(ID, FIRST, LAST);
        String chgFirstName = "태현";
        String chgLastName = "공";
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // when
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.changeName(chgFirstName, chgLastName);
        em.clear();
        transaction.commit();

        // then
        em.clear();
        Customer updateEntity = em.find(Customer.class, ID);
        assertThat(Objects.equals(updateEntity.getFirstName(), chgFirstName), is(false));
        assertThat(Objects.equals(updateEntity.getLastName(), chgLastName), is(false));
    }
}
