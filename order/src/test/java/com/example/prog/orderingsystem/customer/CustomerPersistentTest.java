package com.example.prog.orderingsystem.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Customer 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
class CustomerPersistentTest {

    @Autowired
    EntityManagerFactory managerFactory;

    @DisplayName("Customer repository 접근 가능 테스트")
    @Test
    void connect_repository_test() {
        assertThat(managerFactory).isNotNull();
    }

    @DisplayName("Customer 생성 성공 테스트")
    @Test
    void create_customer_sccess() {
        // given
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Customer customer = CustomerFactory.getNewCustomer();

        // when
        et.begin();
        em.persist(customer);
        Customer savedCustomer = em.find(Customer.class, customer.getCustomerId());
        et.rollback();
        em.close();
        // That
        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @DisplayName("Customer 업데이트 성공 테스트")
    @Test
    void update_customer_success() {
        // given
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Customer customer = CustomerFactory.getNewCustomer();

        String newName = "tester";

        // when
        et.begin();
        em.persist(customer);
        customer.rename(newName);

        Customer updatedCustomer = em.find(Customer.class, customer.getCustomerId());
        et.rollback();
        // That
        assertAll(
                () -> assertThat(updatedCustomer.getLastName()).isEqualTo(customer.getLastName()),
                () -> assertThat(updatedCustomer.getFirstName()).isEqualTo(newName),
                () -> assertThat(updatedCustomer.getCustomerId()).isEqualTo(customer.getCustomerId())
        );
    }

    @DisplayName("Customer 삭제 성공 테스트")
    @Test
    void delete_customer_success() {
        // given
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Customer customer = CustomerFactory.getNewCustomer();

        et.begin();
        em.persist(customer);

        Customer persistCustomer = em.find(Customer.class, customer.getCustomerId());

        // when
        em.remove(persistCustomer);
        et.rollback();

        // That
        assertThat(em.find(Customer.class, persistCustomer.getCustomerId())).isNull();
    }
}
