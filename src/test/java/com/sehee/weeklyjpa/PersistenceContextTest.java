package com.sehee.weeklyjpa;

import com.sehee.weeklyjpa.domain.Customer;
import com.sehee.weeklyjpa.domain.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {
    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;
    Customer customer;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    void persistNewCustomer() {
        customer = new Customer();
        customer.setId(1L);
        customer.setLastName("sehee");
        customer.setFirstName("Lee");
        entityManager.persist(customer);
        log.info("persist!");
    }

    @Test
    @DisplayName("트랜젝션 커밋으로 새로 생성된 엔티티를 저장할 수 있다.")
    void saveByTransaction() {
        persistNewCustomer();

        transaction.begin();
        transaction.commit();
        log.info("COMMIT & FLUSH!");

//         사실상 "커밋 전"에 영속 상태이면 트랜잭션 안이 아니라도 커밋된다.
        log.info("[entityManager.contains] {}", entityManager.contains(customer)); // 영속 상태
    }

    @Test
    @DisplayName("엔티티 매니저 flush로 새로 생성된 엔티티를 동기화할 수 있다.")
    void syncDbByFlush() {
        transaction.begin();
        persistNewCustomer();

        Customer retrievedCustomer = entityManager.find(Customer.class, customer.getId()); // 쿼리 출력X(캐시)
        log.info("[retrivedCustomer] {} {}", retrievedCustomer.getFirstName(), retrievedCustomer.getLastName()); // Lee sehee

        entityManager.flush(); // TransactionRequiredException: 트랜잭션이 시작 안되면 발생
        log.info("FLUSH!"); // 쿼리 출력(저장은 안되고 DB 동기화가 일어나는 것 같다. DB에 안보임.)

        customer.setFirstName("kang"); // 영속성 컨텍스트, 1차 캐시에는 반영. DB 동기화 반영이 안됨.
        Customer retrievedCustomer2 = entityManager.find(Customer.class, customer.getId()); // 쿼리 출력X
        log.info("[retrievedCustomer2] {} {}", retrievedCustomer2.getFirstName(), retrievedCustomer2.getLastName()); // kang sehee

        entityManager.detach(customer); // 1차 캐시에서 조회 못하도록 준영속 상태
        log.info("[Does customer managed at persist context?] {}", entityManager.contains(customer));
        Customer retrievedCustomer3 = entityManager.find(Customer.class, customer.getId()); // 쿼리는 출력(실제 DB에는 저장되지 않은 상태. DB 트랜잭션 작업에 동기화만 된 것이다.)
        log.info("[retrievedCustomer3] {} {}", retrievedCustomer3.getFirstName(), retrievedCustomer3.getLastName()); // Lee sehee

        transaction.commit();
        log.info("COMMIT! REAL SAVE!"); // 이 시점에 실제 DB에 보인다. 커밋이 되어야 실제 DB에 저장된다.
    }

    @Test
    @DisplayName("1차 캐시에서 조회가 가능하다")
    void findInFirstCache() {
        transaction.begin();
        persistNewCustomer();
        transaction.commit(); // 커밋 되고 영속 상태. 1차 캐시에 저장, DB 저장
        log.info("COMMIT & FLUSH!");

        entityManager.find(Customer.class, customer.getId()); // 레포지토리 아님. 쿼리 출력X
    }

    @Test
    @DisplayName("1차 캐시가 아닌 DB에서 조회가 가능하다")
    void findInDB() {

        transaction.begin();
        persistNewCustomer();
        transaction.commit(); // 커밋 되고 영속 상태. 1차 캐시에 저장, DB 저장.
        log.info("COMMIT & FLUSH!");

        entityManager.detach(customer); // 준영속 상태. 1차 캐시 저장 X. DB 저장.
        log.info("DETACH!");
        log.info("[entityManager.contains] {}", entityManager.contains(customer)); // 준영속 상태

        entityManager.find(Customer.class, customer.getId()); // 쿼리 출력
    }
    @Test
    @DisplayName("엔티티를 commit으로 업데이트 할 수 있다.")
    void update() {
        transaction.begin();
        persistNewCustomer();
        transaction.commit();
        log.info("COMMIT & FLUSH!");

        transaction.begin();
        Customer retrievedCustomer = entityManager.find(Customer.class, customer.getId());
        retrievedCustomer.setFirstName("Kang");
        transaction.commit(); // dirty checking & DB에 자동으로 update
        log.info("COMMIT & FLUSH!");
    }
    @Test
    @DisplayName("")
    void delete() {
        transaction.begin();
        persistNewCustomer();
        transaction.commit();
        log.info("COMMIT & FLUSH!");

        transaction.begin();
        entityManager.remove(customer); // 삭제
        log.info("REMOVE(PERSIST CONTEXT)");
        log.info("[entityManager.contains] {}", entityManager.contains(customer));
        transaction.commit();
        log.info("COMMIT & FLUSH!");
    }
}
