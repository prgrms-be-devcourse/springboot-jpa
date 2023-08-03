package com.programmers.week.customer.infra;

import com.programmers.week.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    CustomerRepository customerRepository;

    @ParameterizedTest
    @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|')
    void create_Customer_Success(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(firstName, lastName);
        em.persist(customer);
        transaction.commit();

        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
    }

    @ParameterizedTest
    @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|')
    void find_Customer_Using_1st_Cache_Success(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(firstName, lastName);
        em.persist(customer);
        transaction.commit();
        Customer findCustomer = em.find(Customer.class, customer.getId());

        assertEquals(firstName, findCustomer.getFirstName());
        assertEquals(lastName, findCustomer.getLastName());
        assertEquals(customer.getId(), findCustomer.getId());
    }

    @ParameterizedTest
    @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|')
    void find_Customer_Directly_Success(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(firstName, lastName);
        em.persist(customer);
        transaction.commit();
        em.clear();
        Customer findCustomer = em.find(Customer.class, customer.getId());

        assertEquals(firstName, findCustomer.getFirstName());
        assertEquals(lastName, findCustomer.getLastName());
        assertEquals(customer.getId(), findCustomer.getId());
    }

    @ParameterizedTest
    @CsvSource(value = {"은지|박|영경|나", "명한|유|상민|박", "범준|고|건희|원"}, delimiter = '|')
    void update_Customer_Success(String firstName, String lastName, String newFirstName, String newLastName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(firstName, lastName);
        em.persist(customer);

        customer.changeName(newFirstName, newLastName);
        transaction.commit();

        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertEquals(findCustomer.getId(), customer.getId());
        assertEquals(newFirstName, findCustomer.getFirstName());
        assertEquals(newLastName, findCustomer.getLastName());
    }

    @ParameterizedTest
    @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|')
    void delete_Customer_Success(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer(firstName, lastName);
        em.persist(customer);

        em.remove(customer);
        transaction.commit();

        assertNull((em.find(Customer.class, customer.getId())));
    }

}
