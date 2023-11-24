package com.example.jpa;

import com.example.jpa.customer.Customer;
import com.example.jpa.customer.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PersistenceContextTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    CustomerRepository jpaCustomerRepository;

    static Customer customer;
    static Customer customerSaved;
    @BeforeEach
    void setup(){
        customerSaved = new Customer();
        customerSaved.setName("name saved");
        customerSaved.setPassword("password saved");
        customerSaved.setNickname("nickname saved");

        jpaCustomerRepository.save(customerSaved);


        customer = new Customer();
        customer.setName("name");
        customer.setPassword("password");
        customer.setNickname("nickname");
    }

    @AfterEach
    void clear(){
        jpaCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적으로 회원을 조회한다.")
    void findSuccess(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
        transaction.commit();
        em.close();

        assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("detach로 인한 update 실패")
    void updateFailWithDetach(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);
        em.detach(customer);
        customer.setUsername("test update");

        transaction.commit();
        em.close();

        List<Customer> allCustomers = jpaCustomerRepository.findAll();
        assertThat(allCustomers.get(1).getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("정상적으로 회원을 저장한다.")
    void saveSuccess(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);

        transaction.commit();
        em.close();

        List<Customer> allCustomers = jpaCustomerRepository.findAll();
        assertThat(allCustomers).hasSize(2);
    }



    @Test
    @DisplayName("정상적으로 회원을 수정한다.(dirtyChecking)")
    void updateSuccessWithDirtyChecking(){
        String updateName = "name update";

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);
        customer.setName(updateName);

        transaction.commit();
        em.close();

        Customer allCustomers = jpaCustomerRepository.findAll().get(1);
        assertThat(allCustomers.getName()).isEqualTo(updateName);
    }
    @Test
    @DisplayName("정상적으로 회원을 수정한다.(merge)")
    void updateSuccessWithMerge(){
        String updateName = "name update";

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);
        customer.setName(updateName);
        em.merge(customer);

        transaction.commit();
        em.close();

        Customer allCustomers = jpaCustomerRepository.findAll().get(1);
        assertThat(allCustomers.getName()).isEqualTo(updateName);
    }
    @Test
    @DisplayName("정상적으로 회원을 삭제한다.")
    void deleteSuccess(){

        Customer allCustomers = jpaCustomerRepository.findAll().get(0);


        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customerById = em.find(Customer.class, allCustomers.getId());
        em.remove(customerById);

        transaction.commit();
        em.close();

        List<Customer> afterDelete = jpaCustomerRepository.findAll();

        assertThat(afterDelete).hasSize(0);
    }
}
