package com.prgms.mission_jpa.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CustomerPersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장한다.")
    void saveCustomerInPersistenceContext() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = Customer.builder().firstName("yongsu").lastName("kang").build();
        em.persist(customer);
        transaction.commit();

        assertThat(customerRepository.findById(customer.getId())).isPresent();
    }

    @Test
    @DisplayName("DB에서 고객을 조회한다.")
    void findCustomerFromDB() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = Customer.builder().firstName("yongsu").lastName("kang").build();
        em.persist(customer);
        transaction.commit();

        em.clear();
        assertThat(em.contains(customer)).isFalse();
        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertThat(findCustomer).isNotNull();
    }

    @Test
    @DisplayName("1차 캐시를 이용한 고객 조회")
    void findCustomerInCache() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = Customer.builder().firstName("yongsu").lastName("kang").build();
        em.persist(customer);
        transaction.commit();

        assertThat(em.contains(customer)).isTrue();
        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("변경감지를 통해 수정할 수 없다.")
    void updateInContext(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        String updateFirstName = "dragon";
        String updateLastName = "kim";
        transaction.begin();
        Customer customer = Customer.builder().firstName("yongsu").lastName("kang").build();
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName(updateFirstName);
        customer.setLastName(updateLastName);
        transaction.commit();

        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertThat(findCustomer.getFirstName()).isEqualTo(updateFirstName);
        assertThat(findCustomer.getLastName()).isEqualTo(updateLastName);
    }

    @Test
    @DisplayName("영속성 컨텍스트내에 존재하지 않으면 변경감지를 통해 수정할 수 없다.")
    void updateNotInContext(){
        String updateFirstName = "dragon";
        String updateLastName = "kim";
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = Customer.builder().firstName("yongsu").lastName("kang").build();
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName(updateFirstName);
        customer.setLastName(updateLastName);
        em.clear();
        transaction.commit();

        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertThat(findCustomer.getFirstName()).isNotEqualTo(updateFirstName);
        assertThat(findCustomer.getLastName()).isNotEqualTo(updateLastName);
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void deleteCustomer(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = Customer.builder().firstName("yongsu").lastName("kang").build();
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        em.remove(customer);
        transaction.commit();

        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }
}