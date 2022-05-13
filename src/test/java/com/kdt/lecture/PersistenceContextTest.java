package com.kdt.lecture;

import com.kdt.lecture.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@DataJpaTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("영속화 테스트")
    void persistTest(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        var customer = new Customer(1L, "hj", "kim");

        em.persist(customer);   //  비영속 -> 영속
        transaction.commit();   //entityManager.flush()
    }


    @Test
    @DisplayName("데이터베이스 조회")
    void findDatabaseTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        var customer = new Customer(1L, "hj", "kim");

        em.persist(customer);   //  비영속 -> 영속
        transaction.commit();   //  entityManager.flush()

        log.info("detach : Customer Context {}", customer.getId());
        em.detach(customer);    //  영속 -> 비영속

        //1차 캐시에 대상이 존재하지 않으므로 select 쿼리가 실행됨
        var selected = em.find(Customer.class, 1L);
    }

    @Test
    @DisplayName("1차캐시 조회")
    void findContextTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        var customer = new Customer(1L, "hj", "kim");

        em.persist(customer);   //  비영속 -> 영속
        transaction.commit();   //entityManager.flush()

        //영속성 컨텍스트에 이미 존재하므로 쿼리가 실행되지 않음.
        var selected = em.find(Customer.class, 1L);
    }

    @Test
    @DisplayName("변경 감지")
    void dirtyCheckingTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        var customer = new Customer(1L, "hj", "kim");

        em.persist(customer);   //  비영속 -> 영속
        transaction.commit();   //entityManager.flush()


        transaction.begin();
        var selected = em.find(Customer.class, 1L);
        selected.setLastName("lee");

        //영속성 컨텍스트에 해당 객체가 존재하므로 변경 감지가 작동 - UPDATE 쿼리 실행됨
        transaction.commit();
    }
    @Test
    @DisplayName("영속성 컨텍스트 삭제")
    void removeContextTest(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        var customer = new Customer(1L, "hj", "kim");

        em.persist(customer);   //  비영속 -> 영속
        transaction.commit();   //entityManager.flush()

        transaction.begin();

        var selected = em.find(Customer.class, 1L);
        em.remove(selected);    // 영속성 컨텍스트에서 분리, DB에서 삭제

        transaction.commit();   //entityManager.flush() - delete 쿼리 실행
    }
}