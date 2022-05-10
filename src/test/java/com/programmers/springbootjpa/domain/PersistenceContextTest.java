package com.programmers.springbootjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
class PersistenceContextTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객 정보 저장")
    void saveTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // 테이블의 레코드가 변경되는 작업은 트랜잭션 안에서 동작해야 함
        transaction.begin();

        // 비영속 상태의 entity
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("Jung");

        // 비영속 상태애서 영속 상태로 만들어줌
        entityManager.persist(customer);

        // 트랜잭션 commit을 하게 되면 entityManager.flush()가 자동으로 일어남
        transaction.commit();
    }

    @Test
    @DisplayName("고객 정보 조회: 1) DB 조회")
    void findFromDbTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // 테이블의 레코드가 변경되는 작업은 트랜잭션 안에서 동작해야 함
        transaction.begin();

        // 비영속 상태의 entity
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("Jung");

        // 비영속 상태애서 영속 상태로 만들어줌
        entityManager.persist(customer);

        // 트랜잭션 commit을 하게 되면 entityManager.flush()가 자동으로 일어남
        transaction.commit();

        // 영속 상태에서 준영속 상태로 바꿈
        entityManager.detach(customer);

        Customer selectedCustomer = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selectedCustomer.getFirstName(), selectedCustomer.getLastName());
    }

    @Test
    @DisplayName("고객 정보 조회: 2) 1차 캐시 이용")
    void findFromFirstLevelCacheTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // 테이블의 레코드가 변경되는 작업은 트랜잭션 안에서 동작해야 함
        transaction.begin();

        // 비영속 상태의 entity
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("Jung");

        // 비영속 상태애서 영속 상태로 만들어줌
        entityManager.persist(customer);

        // 트랜잭션 commit을 하게 되면 entityManager.flush()가 자동으로 일어남
        transaction.commit();

        // customer가 영속 상태에 있기 때문에
        // DB에 쿼리를 날리지 않고 1차 캐시에서 값을 가져옴
        Customer selectedCustomer = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selectedCustomer.getFirstName(), selectedCustomer.getLastName());
    }

    @Test
    @DisplayName("고객 정보 수정 (변경 감지)")
    void updateTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // 테이블의 레코드가 변경되는 작업은 트랜잭션 안에서 동작해야 함
        transaction.begin();

        // 비영속 상태의 entity
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("Jung");

        // 비영속 상태애서 영속 상태로 만들어줌
        entityManager.persist(customer);
        // 트랜잭션 commit을 하게 되면 entityManager.flush()가 자동으로 일어남
        transaction.commit();

        // 트랜잭션 다시 begin
        transaction.begin();
        // customer 값 수정
        customer.setFirstName("updatedFirst");
        customer.setLastName("updatedLast");
        // 트랜잭션 commit
        // -> 스냅샷과 달라서 UPDATE 쿼리가 날아감
        transaction.commit();
    }

    @Test
    @DisplayName("고객 정보 삭제")
    void deleteTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // 테이블의 레코드가 변경되는 작업은 트랜잭션 안에서 동작해야 함
        transaction.begin();

        // 비영속 상태의 entity
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("Jung");

        // 비영속 상태애서 영속 상태로 만들어줌
        entityManager.persist(customer);
        // 트랜잭션 commit을 하게 되면 entityManager.flush()가 자동으로 일어남
        transaction.commit();

        // 트랜잭션 다시 begin
        transaction.begin();
        // remove를 해서 영속 상태 -> 비영속 상태로 변경, DB까지 삭제됨
        entityManager.remove(customer);

        // 트랜잭션 commit
        // DELETE 쿼리가 날아감
        transaction.commit();
    }
}