package com.example.springbootjpa;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.example.springbootjpa.domain.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class PersistenceContextLifeCycleTest {

    @Autowired
    EntityManagerFactory emf;

    private Customer customer = new Customer(1L,"suy2on",25);


    @Test
    void persist(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(customer);
        assertThat(entityManager.contains(customer), is(true));
        transaction.commit();
        assertThat(entityManager.contains(customer), is(true));



    }

    @Test
    void detach() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.detach(customer);
        assertThat(entityManager.contains(customer), is(false));
        transaction.commit();
        assertThat(entityManager.contains(customer), is(false));


    }

    @Test
    void remove() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(customer);
        entityManager.remove(customer);
        transaction.commit();
        assertThat(entityManager.contains(customer), is(false));

    }


    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer entity = entityManager.find(Customer.class, 1L);
        entityManager.remove(entity);



    }



}