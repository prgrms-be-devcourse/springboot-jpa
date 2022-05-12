package com.example.springbootjpa.mission2;

import com.example.springbootjpa.mission1.Customer;
import com.example.springbootjpa.mission1.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    Customer customer = new Customer();

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        customer.setId(1L);
        customer.setFirstName("tester");
        customer.setLastName("customer");
        em.persist(customer);
        transaction.commit();
        em.clear();
    }

    @Test
    @DisplayName("Customer Create Test")
    void createCustomer(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer newCustomer = new Customer();
        newCustomer.setId(2L);
        newCustomer.setFirstName("newTester");
        newCustomer.setLastName("newCustomer");

        em.persist(newCustomer);
        transaction.commit();
        em.clear();

        Customer findCustomer = em.find(Customer.class, 2L);

        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(newCustomer);
    }

    @Test
    @DisplayName("Find Customer from DB Test")
    void findCustomerWithDB(){
        EntityManager em = emf.createEntityManager();

        Customer findCustomer = em.find(Customer.class, 1L);

        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("Find Customer from Cache Test")
    void findCustomerWithCache(){
        EntityManager em = emf.createEntityManager();

        Customer findCustomer = em.find(Customer.class, 1L);

        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("Update Customer Test")
    void updateCustomer(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer findCustomer = em.find(Customer.class, 1L);
        findCustomer.setFirstName("Updated");
        transaction.commit();
        em.clear();

        Customer updatedCustomer = em.find(Customer.class, 1L);

        assertThat(updatedCustomer).usingRecursiveComparison().isEqualTo(findCustomer);
    }

    @Test
    @DisplayName("Delete Customer Test")
    void deleteCustomer(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer findCustomer = em.find(Customer.class, 1L);
        em.remove(findCustomer);
        transaction.commit();
        em.clear();

        Customer updatedCustomer = em.find(Customer.class, 1L);

        assertThat(updatedCustomer).isNull();
    }

}
