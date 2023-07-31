package com.programmers.jpa_mission.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {
    @Autowired
    EntityManagerFactory emf;
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
    }


    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    @Test
    void save() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");

        entityManager.persist(customer);//영속화

        transaction.commit();
    }

    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    @Test
    void saveAndFlush() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");

        entityManager.persist(customer);//영속화
        entityManager.flush();

        transaction.commit();
    }

    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    @Test
    void saveAll() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");
        Customer customer2 = new Customer("Beom", "Kim");
        List<Customer> list = List.of(customer2, customer);

        for (Customer c : list) {
            entityManager.persist(c);//영속화
        }

        transaction.commit();
    }


    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    @Test
    void 조회_1차캐시_이용() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");
        entityManager.persist(customer);

        transaction.commit();

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    //    Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0 where c1_0.id=?
    @Test
    void 조회_DB_이용() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");
        entityManager.persist(customer);

        transaction.commit();

        entityManager.detach(customer);

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    //    Hibernate: update customers set first_name=?,last_name=? where id=?
    @Test
    void update1() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");
        entityManager.persist(customer);

        transaction.commit();

        transaction.begin();

        if (!entityManager.contains(customer)) {
            entityManager.persist(customer);
        } else {
            entityManager.merge(customer);
        }

        customer.update("ChulBeom");

        transaction.commit();
    }

    //    Hibernate: select next value for customers_SEQ
    //    Hibernate: insert into customers (first_name,last_name,id) values (?,?,?)
    //    Hibernate: update customers set first_name=?,last_name=? where id=?
    @Test
    void update2() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");
        entityManager.persist(customer);

        customer.update("ChulBeom");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomChul", "Shin");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer selected = entityManager.find(Customer.class, 1L);
        entityManager.remove(selected);

        transaction.commit();
    }
}
