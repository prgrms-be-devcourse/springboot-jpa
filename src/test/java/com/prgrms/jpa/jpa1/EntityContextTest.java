package com.prgrms.jpa.jpa1;

import com.prgrms.jpa.jpa1.domain.Customer;
import com.prgrms.jpa.jpa1.repository.CustomerRepository;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class EntityContextTest {
    @Autowired
    CustomerRepository repository;

    //@Autowired
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp(){
        repository.deleteAll();
    }

    @Test
    void save(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer(); // 비영속 상태
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("surin");

        entityManager.persist(customer); // 비영속 -> 영속상태 == 영속화
        transaction.commit(); // entityManager.flush(); 가 자동으로 이루어진다.

        // entityManager.detach(customer);

        Customer findCustomer = entityManager.find(Customer.class, 1L); // 이 상태가 1차 캐시에 들어가게 되면서 조회가 가능해진다.

        assertThat(findCustomer.getFirstName())
                .isEqualTo("lee");

        assertThat(findCustomer.getLastName())
                .isEqualTo("surin");

    }

    @Test
    void update(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer(); // 비영속 상태
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("surin");

        entityManager.persist(customer); // 비영속 -> 영속상태 == 영속화
        transaction.commit(); // entityManager.flush(); 가 자동으로 이루어진다.

        // entityManager.detach(customer);

        transaction.begin();
        customer.setFirstName("lee");
        customer.setLastName("merong");
        transaction.commit();
    }

    @Test
    void remove(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer(); // 비영속 상태
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("surin");

        entityManager.persist(customer); // 비영속 -> 영속상태 == 영속화
        transaction.commit(); // entityManager.flush(); 가 자동으로 이루어진다.

        // entityManager.detach(customer);

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();
    }
}
