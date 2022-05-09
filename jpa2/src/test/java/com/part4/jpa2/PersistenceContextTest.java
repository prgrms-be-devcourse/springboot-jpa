package com.part4.jpa2;

import com.part4.jpa2.domain.Customer;
import com.part4.jpa2.domain.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("영속성컨텍스트의 엔티티 생명주기 실습")
public class PersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void 저장(){
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        var customer = new Customer(); // 비영속상태
        customer.setFirstName("subin");
        customer.setLastName("kim");
        em.persist(customer); // 비영속 -> 영속 (영속화)

        transaction.commit(); // em.flush() 자동으로 일어남

        em.detach(customer); // 영속 -> 준영속
        var selected = em.find(Customer.class, customer.getId());
        System.out.print(selected.getFirstName());
        System.out.println(selected.getLastName());
    }

    @Test
    void 조회_1차캐시_이용(){
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        var customer = new Customer();
        customer.setFirstName("subin");
        customer.setLastName("kim");
        em.persist(customer);
        transaction.commit();

        var selected = em.find(Customer.class, customer.getId());
        System.out.print(selected.getFirstName());
        System.out.println(selected.getLastName());
    }

    @Test
    void 수정(){
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        var customer = new Customer();
        customer.setFirstName("subin");
        customer.setLastName("kim");
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName("SUBIN");
        customer.setLastName("KIM");
        transaction.commit();
    }

    @Test
    void 삭제(){
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        var customer = new Customer();
        customer.setFirstName("subin");
        customer.setLastName("kim");
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        em.remove(customer);
        transaction.commit();
        assertThat(customerRepository.count()).isEqualTo(0);
    }
}

