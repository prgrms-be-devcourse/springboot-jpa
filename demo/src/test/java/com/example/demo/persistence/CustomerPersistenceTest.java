package com.example.demo.persistence;

import com.example.demo.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class CustomerPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    private EntityManager entityManager;

    private EntityTransaction transaction;

    @Test
    void 조회_1차캐시_이용() {

        createTransaction();
        Customer customer = persistCustomer();

        assertThat(entityManager.find(Customer.class, customer.getId())).isEqualTo(customer);
    }

    @Test
    void 조회_DB_이용() {
        createTransaction();
        Customer customer = persistCustomer();
       // transaction.begin();
        entityManager.detach(customer);
      //  transaction.commit();

        //DB에 다녀오면 참조값이 같지 않다.
        assertThat(entityManager.find(Customer.class, customer.getId())).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void persist인객체는_수정시_save하지않아도_update된다() {
        createTransaction();
        Customer customer = persistCustomer();

       // transaction.begin();
        customer.changeName("change", "name");
      //  transaction.commit();

        assertThat(entityManager.find(Customer.class, customer.getId()).getFirstName()).isEqualTo("change");
    }

    @Test
    void DB에서_삭제() {
        createTransaction();
        Customer customer = persistCustomer();

      //  transaction.begin();
        entityManager.remove(customer);
      //  transaction.commit();
        entityManager.clear();


        log.info("{}", entityManager.find(Customer.class,customer.getId()));
        assertThat(entityManager.find(Customer.class, customer.getId())).isNull();
    }

    private Customer persistCustomer() {
        transaction.begin();

        Customer customer = new Customer("yerim", "lee");
        entityManager.persist(customer);

        transaction.commit();
        return customer;
    }

    private void createTransaction(){
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
    }

}
